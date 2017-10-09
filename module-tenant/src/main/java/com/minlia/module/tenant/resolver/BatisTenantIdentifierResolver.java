package com.minlia.module.tenant.resolver;

/**
 * 当前租户标识符提供器
 */
public interface BatisTenantIdentifierResolver {

  String resolveCurrentTenantIdentifier();

}
