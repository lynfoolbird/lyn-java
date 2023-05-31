package com.lynjava.ddd.domain.shared.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 资源申请表
 *
 * @author li
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddd_resource_apply_t")
@SuperBuilder(toBuilder = true)
public class ResourceApplyPO extends BaseEntity {
    /**
     * 类型
     */
    private String category;

    /**
     * 状态：初始化、执行中、成功、失败
     */
    private Integer status;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    private String tenantId;
}
