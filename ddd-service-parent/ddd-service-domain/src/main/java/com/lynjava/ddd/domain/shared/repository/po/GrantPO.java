package com.lynjava.ddd.domain.shared.repository.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lynjava.ddd.domain.shared.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 授权表
 *
 * @author li
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddd_grant_t")
@SuperBuilder(toBuilder = true)
public class GrantPO extends BaseEntity {
    /**
     * 授权对象类型
     */
    private String objectType;

    /**
     * 授权对象id
     */
    private String objectId;

    /**
     * 描述
     */
    private String description;

    /**
     * appId
     */
    @TableField(value = "app_id")
    private String appId;
}
