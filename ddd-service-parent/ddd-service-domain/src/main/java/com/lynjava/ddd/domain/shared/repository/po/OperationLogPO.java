package com.lynjava.ddd.domain.shared.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 操作记录表
 *
 * @author li
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddd_operation_log_t")
@SuperBuilder(toBuilder = true)
public class OperationLogPO extends BaseEntity {
    /**
     * 模块
     */
    private String module;

    /**
     * 动作
     */
    private String action;

    /**
     * 业务id
     */
    @TableField(value = "business_id")
    private String businessId;

    /**
     * 批次号（比如每次接口请求生成一个放在RequestContext中）
     */
    @TableField(value = "batch_id")
    private String batchId;

    /**
     * 操作对象
     */
    private String target;

    /**
     * 内容
     */
    private String content;

    /**
     * 操作结果
     */
    private String status;

    /**
     * 租户项目id
     */
    @TableField(value = "app_id")
    private String appId;

    /**
     * 租户企业id
     */
    @TableField(value = "tenant_id")
    private String tenantId;
}
