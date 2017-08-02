package com.minlia.cloud.tenant.context;


import com.minlia.cloud.tenant.core.TenantCoreVersion;
import com.minlia.cloud.tenant.domain.Tenant;

public class TenantContextImpl implements TenantContext {

    private static final long serialVersionUID = TenantCoreVersion.SERIAL_VERSION_UID;

    private Tenant tenant;

    @Override
    public Tenant getTenant() {
        return tenant;
    }

    @Override
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}