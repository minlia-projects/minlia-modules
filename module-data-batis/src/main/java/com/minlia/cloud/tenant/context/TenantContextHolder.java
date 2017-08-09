package com.minlia.cloud.tenant.context;


import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;

public class TenantContextHolder {
    // ~ Static fields/initializers
    // =====================================================================================

    public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
    public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";
    public static final String MODE_GLOBAL = "MODE_GLOBAL";
    public static final String SYSTEM_PROPERTY = "spring.security.strategy";
    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);
    private static TenantContextHolderStrategy strategy;
    private static int initializeCount = 0;

    static {
        initialize();
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * Explicitly clears the context value from the current thread.
     */
    public static void clearContext() {
        strategy.clearContext();
    }

    /**
     * Obtain the current <code>TenantContext</code>.
     *
     * @return the security context (never <code>null</code>)
     */
    public static TenantContext getContext() {
        return strategy.getContext();
    }

    /**
     * Primarily for troubleshooting purposes, this method shows how many times the class
     * has re-initialized its <code>TenantContextHolderStrategy</code>.
     *
     * @return the count (should be one unless you've called
     * {@link #setStrategyName(String)} to switch to an alternate strategy.
     */
    public static int getInitializeCount() {
        return initializeCount;
    }

    private static void initialize() {
        if (!StringUtils.hasText(strategyName)) {
            // Set default
            strategyName = MODE_THREADLOCAL;
        }

        if (strategyName.equals(MODE_THREADLOCAL)) {
            strategy = new ThreadLocalTenantContextHolderStrategy();
//        } else if (strategyName.equals(MODE_INHERITABLETHREADLOCAL)) {
//            strategy = new InheritableThreadLocalTenantContextHolderStrategy();
//        } else if (strategyName.equals(MODE_GLOBAL)) {
//            strategy = new GlobalTenantContextHolderStrategy();
        } else {
            // Try to load a custom strategy
            try {
                Class<?> clazz = Class.forName(strategyName);
                Constructor<?> customStrategy = clazz.getConstructor();
                strategy = (TenantContextHolderStrategy) customStrategy.newInstance();
            } catch (Exception ex) {
                ReflectionUtils.handleReflectionException(ex);
            }
        }

        initializeCount++;
    }

    /**
     * Associates a new <code>TenantContext</code> with the current thread of execution.
     *
     * @param context the new <code>TenantContext</code> (may not be <code>null</code>)
     */
    public static void setContext(TenantContext context) {
        strategy.setContext(context);
    }

    /**
     * Changes the preferred strategy. Do <em>NOT</em> call this method more than once for
     * a given JVM, as it will re-initialize the strategy and adversely affect any
     * existing threads using the old strategy.
     *
     * @param strategyName the fully qualified class name of the strategy that should be
     *                     used.
     */
    public static void setStrategyName(String strategyName) {
        TenantContextHolder.strategyName = strategyName;
        initialize();
    }

    /**
     * Allows retrieval of the context strategy. See SEC-1188.
     *
     * @return the configured strategy for storing the security context.
     */
    public static TenantContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }

    /**
     * Delegates the creation of a new, empty context to the configured strategy.
     */
    public static TenantContext createEmptyContext() {
        return strategy.createEmptyContext();
    }

    public String toString() {
        return "TenantContextHolder[strategy='" + strategyName + "'; initializeCount="
                + initializeCount + "]";
    }
}
