package com.lynjava.ddd.domain.shared.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 配置表
 *
 * property、group、category、tenant 唯一
 *
 * @author li
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddd_property_t")
@SuperBuilder(toBuilder = true)
public class PropertyPO extends BaseEntity {
    /**
     * 配置key，以子域.开头
     */
    private String property;

    /**
     * 配置value
     */
    private String value;

    /**
     * 描述
     */
    private String description;

    /**
     * 配置分组
     */
    private String group;

    /**
     * 配置类型：platform(平台级)、tenant(租户级)
     */
    private String category;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    private String tenantId;
}
