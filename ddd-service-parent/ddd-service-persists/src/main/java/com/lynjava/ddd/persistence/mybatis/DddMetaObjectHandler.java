package com.lynjava.ddd.persistence.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class DddMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "create_date", Date.class, new Date());
        this.strictInsertFill(metaObject, "create_by", String.class, "admin");
        this.strictInsertFill(metaObject, "is_deleted", String.class, "N");
        this.strictInsertFill(metaObject, "updated_date", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updated_by", String.class, "admin");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updated_date", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updated_by", String.class, "admin");
    }
}
