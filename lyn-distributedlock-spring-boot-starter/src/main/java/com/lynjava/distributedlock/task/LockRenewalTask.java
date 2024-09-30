package com.lynjava.distributedlock.task;

import com.lynjava.distributedlock.config.DbDistributedLockProperties;
import com.lynjava.distributedlock.entity.DbDistributedLockPO;
import com.lynjava.distributedlock.mapper.DbDistributedLockDao;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockRenewalTask implements Runnable {

    private final Map<String, DbDistributedLockPO> lockRecordMap = new ConcurrentHashMap<>();

    private final DbDistributedLockDao dbDistributedLockDao;

    private final DbDistributedLockProperties distributedLockProperties;
    public LockRenewalTask(DbDistributedLockDao dbDistributedLockDao, DbDistributedLockProperties distributedLockProperties) {
        this.dbDistributedLockDao = dbDistributedLockDao;
        this.distributedLockProperties = distributedLockProperties;
    }
    @Override
    public void run() {
        for (Map.Entry<String, DbDistributedLockPO> entry : this.lockRecordMap.entrySet()) {
            this.renewalLock(entry.getValue());
        }
    }

    private void renewalLock(DbDistributedLockPO dbDistributedLockPO) {
        try {
            if (!this.isNeedToRenewal(dbDistributedLockPO)) {
                return;
            }
            LocalDateTime lockExpireTime = LocalDateTime.now().plusSeconds(
                    this.distributedLockProperties.getExclusive().getRenewalTime()
            );
            if (1== this.dbDistributedLockDao.renewalLockExpireTime(dbDistributedLockPO.getLockName(), dbDistributedLockPO.getHolder(), lockExpireTime)) {
                dbDistributedLockPO.setLockExpireTime(lockExpireTime);
            } else {
                this.getLockRecordMap().remove(dbDistributedLockPO.getLockName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNeedToRenewal(DbDistributedLockPO dbDistributedLockPO) {
        LocalDateTime nextRenewTime = dbDistributedLockPO.getLockExpireTime()
                .minusSeconds(this.distributedLockProperties.getExclusive().getRenewalTime() / 2);
        return LocalDateTime.now().isAfter(nextRenewTime);
    }

    public Map<String, DbDistributedLockPO> getLockRecordMap() {
        return lockRecordMap;
    }
}
