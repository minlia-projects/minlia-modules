package org.springframework.data.mybatis.service.sample;

import com.minlia.cloud.data.batis.support.service.BaseService;
import org.springframework.data.mybatis.domain.sample.Role;
import org.springframework.data.mybatis.repository.sample.RoleRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/1/17.
 */
@Transactional
public interface RoleService  extends BaseService<RoleRepository ,Role,Long> {
}
