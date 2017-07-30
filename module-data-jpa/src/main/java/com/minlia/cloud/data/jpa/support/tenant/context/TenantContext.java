package com.minlia.cloud.data.jpa.support.tenant.context;


import com.minlia.cloud.data.jpa.support.tenant.domain.Tenant;

import java.io.Serializable;


public interface TenantContext extends Serializable {
    Tenant getTenant();
    void setTenant(Tenant tenant);
}
