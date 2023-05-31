package com.lynjava.ddd.domain.shared.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 资源申请日志表
 *
 * @author li
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddd_resource_log_t")
@SuperBuilder(toBuilder = true)
public class ResourceLogPO extends BaseEntity {
    /**
     * 申请id
     */
    private String applyId;

    /**
     * 步骤名称
     */
    private String step;

    /**
     * 请求参数
     */
    private String request;

    /**
     * 响应参数
     */
    private String response;

    /**
     * 状态：初始化、执行中、成功、失败
     */
    private Integer status;

    /**
     * 尝试次数
     */
    private Integer tries;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    private String tenantId;
}
