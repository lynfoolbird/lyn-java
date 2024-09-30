package com.lynjava.distributedlock.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lyn.distributedlock")
public class DbDistributedLockProperties {

    private ExclusiveProperties exclusive;
    private ReentrantProperties reentrant;

    public ExclusiveProperties getExclusive() {
        return exclusive == null ? new ExclusiveProperties() : exclusive;
    }

    public void setExclusive(ExclusiveProperties exclusive) {
        this.exclusive = exclusive;
    }

    public ReentrantProperties getReentrant() {
        return reentrant == null ? new ReentrantProperties() : reentrant;
    }

    public void setReentrant(ReentrantProperties reentrant) {
        this.reentrant = reentrant;
    }

    public class ExclusiveProperties {
        // 排他锁续期时间，即续期一次锁增加的有效时间，不能小于2秒
        private int renewalTime = 30;
        // 排他锁续期间隔，即每个多少时间续期一次，有效的续期间隔必须小于续期时间/2
        private int renewalInterval = 1;

        public int getRenewalTime() {
            return renewalTime;
        }

        // 续期时间不能小于2（单位为秒）
        public void setRenewalTime(int renewalTime) {
            if (renewalTime >=2) {
                this.renewalTime = renewalTime;
            }
        }

        public int getRenewalInterval() {
            return renewalInterval;
        }

        // 续期间隔时间不能大于续期时间的一半
        public void setRenewalInterval(int renewalInterval) {
            if (renewalInterval <= this.renewalTime / 2) {
                this.renewalInterval = renewalInterval;
            }
        }
    }


    public class ReentrantProperties {
        // 重入锁加锁失败后重试次数
        private int retryTimes = 2;

        // 重入锁续期时间
        private int renewalTime = 30;

        public int getRetryTimes() {
            return retryTimes;
        }

        public void setRetryTimes(int retryTimes) {
            if (retryTimes > 1) {
                this.retryTimes = retryTimes;
            }
        }

        public int getRenewalTime() {
            return renewalTime;
        }

        public void setRenewalTime(int renewalTime) {
            this.renewalTime = renewalTime;
        }
    }

}
