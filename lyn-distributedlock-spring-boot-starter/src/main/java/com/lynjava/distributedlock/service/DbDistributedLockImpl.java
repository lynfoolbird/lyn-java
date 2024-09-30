package com.lynjava.distributedlock.service;

import com.lynjava.distributedlock.api.IDistributedLock;
import com.lynjava.distributedlock.api.IExpiredReentrantLockCallback;
import com.lynjava.distributedlock.config.DbDistributedLockProperties;
import com.lynjava.distributedlock.entity.DbDistributedLockPO;
import com.lynjava.distributedlock.mapper.DbDistributedLockDao;
import com.lynjava.distributedlock.task.LockRenewalTask;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

public class DbDistributedLockImpl implements IDistributedLock {
    private final LockRenewalTask lockRenewalTask;

    private final DbDistributedLockDao dbDistributedLockDao;

    private final DbDistributedLockProperties dbDistributedLockProperties;

    public DbDistributedLockImpl(LockRenewalTask lockRenewalTask, DbDistributedLockDao dbDistributedLockDao, DbDistributedLockProperties dbDistributedLockProperties) {
        this.lockRenewalTask = lockRenewalTask;
        this.dbDistributedLockDao = dbDistributedLockDao;
        this.dbDistributedLockProperties = dbDistributedLockProperties;
    }
    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public boolean acquireExclusiveLock(String lockName) {
        return this.acquireExclusiveLockFromCache(lockName) || this.acquireExclusiveLockFromDb(lockName);
    }

    private boolean acquireExclusiveLockFromCache(String lockName) {
        Map<String, DbDistributedLockPO> lockMap = this.lockRenewalTask.getLockRecordMap();
        return lockMap.containsKey(lockName) && this.getHolder().equals(lockMap.get(lockName).getHolder())
                && LocalDateTime.now().isBefore(lockMap.get(lockName).getLockExpireTime());
    }

    private boolean acquireExclusiveLockFromDb(String lockName) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DbDistributedLockPO newLock = DbDistributedLockPO.builder()
                .lockName(lockName)
                .lockExpireTime(localDateTime.plusSeconds(this.dbDistributedLockProperties.getExclusive().getRenewalTime()))
                .holder(this.getHolder())
                .build();
        if (this.acquireExclusiveLock(newLock)) {
            this.lockRenewalTask.getLockRecordMap().put(lockName, newLock);
            return true;
        }
        return false;
    }

    private boolean acquireExclusiveLock(DbDistributedLockPO lockPO) {
        DbDistributedLockPO curLockPO = this.dbDistributedLockDao.selectByLockName(lockPO.getLockName());
        if (curLockPO == null) {
            try {
                return 1 == this.dbDistributedLockDao.insertLockRecord(lockPO);
            } catch (DuplicateKeyException e) {
                return false;
            }
        } else if (LocalDateTime.now().isAfter(curLockPO.getLockExpireTime())) {
            return 1 == this.dbDistributedLockDao.updateLockRecordWithLockNum(lockPO, curLockPO.getLockNum());
        } else {
            return false;
        }
    }

    private String getHolder() {
        return "主机ip" + "进程id" + Thread.currentThread().getId();
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public boolean releaseExclusiveLock(String lockName) {
        if (!this.lockRenewalTask.getLockRecordMap().containsKey(lockName)) {
            return true;
        } else if (1==this.dbDistributedLockDao.deleteByLockNameAndHolder(lockName,
                this.lockRenewalTask.getLockRecordMap().get(lockName).getHolder())) {
            this.lockRenewalTask.getLockRecordMap().remove(lockName);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public boolean acquireReentrantLock(String lockName, String holder, String additional) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime lockExpireTime = localDateTime.plusSeconds(this.dbDistributedLockProperties.getReentrant().getRenewalTime()
        );
        DbDistributedLockPO newLock = DbDistributedLockPO.builder()
                .lockName(lockName)
                .lockExpireTime(lockExpireTime)
                .holder(holder)
                .additional(additional)
                .lockNum(1)
                .build();
        DbDistributedLockPO dbDistributedLock = this.dbDistributedLockDao.selectByLockName(lockName);
        if (dbDistributedLock == null) {
            return 1 == this.dbDistributedLockDao.insertLockRecord(newLock);
        } else if (!holder.equals(dbDistributedLock.getHolder()) && LocalDateTime.now().isAfter(dbDistributedLock.getLockExpireTime())
          && tryCleanExpireLockIfAllow(dbDistributedLock)) {
            return 1 == this.dbDistributedLockDao.insertLockRecord(newLock);
        } else if (!holder.equals(dbDistributedLock.getHolder())) {
            return false;
        } else {
            dbDistributedLock.setLockExpireTime(lockExpireTime);
            dbDistributedLock.setHolder(holder);
            dbDistributedLock.setAdditional(additional);
            return 1 == this.dbDistributedLockDao.updateLockRecordWithLockNameAndHolder(dbDistributedLock);
        }
    }

    public boolean acquireReentrantLockRetry(String lockName, String holder, String additional) {
        int retryTimes = this.dbDistributedLockProperties.getReentrant().getRetryTimes();
        while (true) {
            if (retryTimes > 0) {
                boolean res;
                try {
                    return this.acquireReentrantLock(lockName, holder, additional);
                } catch (DuplicateKeyException e) {
                    continue;
                } catch (DeadlockLoserDataAccessException e) {
                    res = false;
                } finally {
                    --retryTimes;
                }
                return res;
            }
            return false;
        }
    }

    private boolean tryCleanExpireLockIfAllow(DbDistributedLockPO dbDistributedLock) {
        // 从容器中获取bean
        IExpiredReentrantLockCallback callback = null;
        if (callback != null && callback.isAllowCleanUp(dbDistributedLock.getLockName(), dbDistributedLock.getHolder(), dbDistributedLock.getAdditional())) {
            return 1 == this.dbDistributedLockDao.deleteByLockNameAndHolder(dbDistributedLock.getLockName(), dbDistributedLock.getHolder());
        }
        return false;
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public boolean releaseReentrantLock(String lockName, String holder) {
        return 1==this.dbDistributedLockDao.reentrantDeleteRecord(lockName, holder)
                || 1==this.dbDistributedLockDao.reentrantMinusLockNum(lockName, holder);
    }
}
