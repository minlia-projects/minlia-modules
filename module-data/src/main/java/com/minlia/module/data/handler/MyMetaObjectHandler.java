package com.minlia.module.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自动填充功能
 * 注解填充字段 @TableField(.. fill = FieldFill.INSERT) 生成器策略部分也可以配置！
 *
 * @author garen
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createBy", Long.class, MinliaSecurityContextHolder.getUid()); // 起始版本 3.3.0(推荐使用)
        // 或者
//        this.strictUpdateFill(metaObject, "createDate", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
        // 或者
//        this.fillStrategy(metaObject, "createDate", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "lastModifiedBy", Long.class, MinliaSecurityContextHolder.getUid()); // 起始版本 3.3.0(推荐)
        // 或者
//        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)
        // 或者
//        this.fillStrategy(metaObject, "updateTime", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
    }
}