package org.springframework.data.mybatis.service.sample;

import com.minlia.cloud.data.batis.support.service.AbstractBaseService;
import org.springframework.data.mybatis.domain.sample.Role;
import org.springframework.data.mybatis.repository.sample.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/1/17.
 */
@Service
public class RoleServiceImpl extends AbstractBaseService<RoleRepository,Role,Long> implements RoleService {





}
