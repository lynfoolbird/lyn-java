package com.lynjava.ddd.persistence.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class DddMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "createdBy", String.class, "admin");
        this.strictInsertFill(metaObject, "isDeleted", String.class, "N");
        this.strictInsertFill(metaObject, "lastUpdatedDate", Date.class, new Date());
        this.strictUpdateFill(metaObject, "lastUpdatedBy", String.class, "admin");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "lastUpdatedDate", Date.class, new Date());
        this.strictUpdateFill(metaObject, "lastUpdatedBy", String.class, "admin");
    }
}
