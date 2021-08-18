//package com.minlia.module.data.interceptor;
//
//import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.LongValue;
//
//public class TenantLineInnerInterceptor implements TenantLineHandler {
//
//    @Override
//    public Expression getTenantId() {
//        return new LongValue(1);
//    }
//
//    // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
//    @Override
//    public boolean ignoreTable(String tableName) {
//        return !"user".equalsIgnoreCase(tableName);
//    }
//
//}