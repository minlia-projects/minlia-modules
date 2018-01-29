//package com.minlia.cloud.tenant.context;
//
//public interface TenantContextHolderStrategy {
//    // ~ Methods
//    // ========================================================================================================
//
//    /**
//     * Clears the current context.
//     */
//    void clearContext();
//
//    /**
//     * Obtains the current context.
//     *
//     * @return a context (never <code>null</code> - create a default implementation if
//     * necessary)
//     */
//    TenantContext getContext();
//
//    /**
//     * Sets the current context.
//     *
//     * @param context to the new argument (should never be <code>null</code>, although
//     *                implementations must check if <code>null</code> has been passed and throw an
//     *                <code>IllegalArgumentException</code> in such cases)
//     */
//    void setContext(TenantContext context);
//
//    /**
//     * Creates a new, empty context implementation, for use by
//     * <tt>TenantContextRepository</tt> implementations, when creating a new context for
//     * the first time.
//     *
//     * @return the empty context.
//     */
//    TenantContext createEmptyContext();
//}
