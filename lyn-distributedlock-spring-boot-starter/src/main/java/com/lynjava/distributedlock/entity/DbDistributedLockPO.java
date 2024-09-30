package com.lynjava.distributedlock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbDistributedLockPO {

    private String id;

    // 锁资资源名称（加唯一索引)
    private String lockName;

    // 锁失效时间
    private LocalDateTime lockExpireTime;
    // 锁持有者
    private String holder;
    // 加锁次数
    private int lockNum = 1;
    // 附加信息
    private String additional;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;


}
