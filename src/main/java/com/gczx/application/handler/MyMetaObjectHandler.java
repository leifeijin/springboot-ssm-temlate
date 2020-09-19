package com.gczx.application.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * @author leifeijin
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.fillStrategy(metaObject, "createTime", new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.fillStrategy(metaObject, "updateTime", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        this.fillStrategy(metaObject, "updateTime", new Date());
    }
}
