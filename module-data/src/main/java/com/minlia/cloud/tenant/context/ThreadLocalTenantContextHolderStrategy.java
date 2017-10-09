//package com.minlia.cloud.tenant.context;
//
//import org.springframework.util.Assert;
//
//final class ThreadLocalTenantContextHolderStrategy implements
//        TenantContextHolderStrategy {
//    // ~ Static fields/initializers
//    // =====================================================================================
//
//    private static final ThreadLocal<TenantContext> contextHolder = new ThreadLocal<TenantContext>();
//
//    // ~ Methods
//    // ========================================================================================================
//
//    public void clearContext() {
//        contextHolder.remove();
//    }
//
//    public TenantContext getContext() {
//        TenantContext ctx = contextHolder.get();
//
//        if (ctx == null) {
//            ctx = createEmptyContext();
//            contextHolder.set(ctx);
//        }
//
//        return ctx;
//    }
//
//    public void setContext(TenantContext context) {
//        Assert.notNull(context, "Only non-null TenantContext instances are permitted");
//        contextHolder.set(context);
//    }
//
//    public TenantContext createEmptyContext() {
//        return new TenantContextImpl();
//    }
//}
