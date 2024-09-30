package com.lynjava.distributedlock.mapper;

import com.lynjava.distributedlock.entity.DbDistributedLockPO;

import java.time.LocalDateTime;

public class DbDistributedLockDao {

    public DbDistributedLockPO selectByLockName(String lockName) {
        return new DbDistributedLockPO();
    }

    public DbDistributedLockPO selectExpireLockByLockName(String lockName) {
        return new DbDistributedLockPO();
    }

    public int deleteExpireRecordByLockName(String lockName) {
        return 1;
    }

    public int insertLockRecord(DbDistributedLockPO record) {
        return 1;
    }

    public int updateLockRecordWithLockNum(DbDistributedLockPO record, int oldLockNum) {
        return 1;
    }

    public int updateLockRecordWithLockNameAndHolder(DbDistributedLockPO record) {
        return 1;
    }

    public int reentrantMinusLockNum(String lockName, String holder) {
        return 1;
    }

    public int reentrantDeleteRecord(String lockName, String holder) {
        return 1;
    }

    public int deleteByLockName(String lockName) {
        return 1;
    }

    public int deleteByLockNameAndHolder(String lockName, String holder) {
        return 1;
    }

    public int renewalLockExpireTime(String lockName, String  holder, LocalDateTime lockExpireTime) {
        return 1;
    }
}
