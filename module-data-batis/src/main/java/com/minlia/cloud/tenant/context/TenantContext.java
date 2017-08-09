package com.minlia.cloud.tenant.context;


import com.minlia.cloud.tenant.domain.Tenant;

import java.io.Serializable;


public interface TenantContext extends Serializable {
    Tenant getTenant();
    void setTenant(Tenant tenant);
}
