package com.lynjava.ddd.domain.cluster.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.Data;

/**
 * 和db表对应
 */
@Data
@TableName("instance_t")
public class InstancePO extends BaseEntity {

    @TableField
    private String hostname;

    @TableField
    private Integer port;

    @TableField
    private Integer clusterId;

}
