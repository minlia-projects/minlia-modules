package com.minlia.cloud.data.batis.support.repository;


import com.minlia.cloud.body.query.Combo;
import com.minlia.cloud.body.query.ComboData;
import com.minlia.cloud.data.batis.support.persistence.entity.AbstractAuditableEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface JpaCustomeRepository<ENTITY extends AbstractAuditableEntity> {

    List<ComboData> findJson(Combo item);

}
