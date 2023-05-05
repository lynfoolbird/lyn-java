package com.lynjava.ddd.domain.shared.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseEntity implements Serializable {

    @TableId(type = IdType.NONE)
    private Integer id;

    @TableField(value = "is_deleted", select = false, fill = FieldFill.INSERT)
    @TableLogic(value = "N", delval = "Y")
    private String isDeleted;

    @TableField(value = "created_date", fill = FieldFill.INSERT)
    private Date createdDate;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "last_updated_date", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdatedDate;

    @TableField(value = "last_updated_by", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;
}
