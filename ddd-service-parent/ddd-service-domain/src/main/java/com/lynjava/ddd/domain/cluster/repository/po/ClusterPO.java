package com.lynjava.ddd.domain.cluster.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynjava.ddd.common.model.BaseEntity;
import lombok.Data;

/**
 * 和db表对应
 */
@Data
@TableName("cluster_t")
public class ClusterPO extends BaseEntity {

    @TableField
    private String name;

    @TableField(exist = false)
    private String category;

}
