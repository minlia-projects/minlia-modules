package com.minlia.modules.rbac.service;

import com.minlia.cloud.service.AbstractReadOnlyService;
import com.minlia.modules.rbac.dao.PermissionDao;
import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 */
@Service
public class PermissionReadOnlyServiceImpl extends AbstractReadOnlyService<PermissionDao,Permission,Long> implements PermissionReadOnlyService {

    @Autowired
    PermissionDao dao;

    @Override
    @Cacheable(value = { "permission" }, key = "#p0",unless="#result==null")
    public List<GrantedAuthority> findPermissionsByRoles(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
    @Cacheable(value = { "permission" }, key = "#p0",unless="#result==null")
    public List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> permissions = new ArrayList<String>();
        final List<Permission> collection = new ArrayList<Permission>();
        //添加角色里面的权限点
        for (final Role role : roles) {

            if(null ==role.getId()){
                break;
            }

            if(null !=role.getPermissions()&& role.getPermissions().size()>0) {
                collection.addAll(role.getPermissions());
            }else{
                //没有带出来, 需要手动查询
                List<Permission> found=dao.findByRoles_Id(role.getId());
                collection.addAll(found);
            }
        }
        //添加权限点
        for (Permission item : collection) {
            permissions.add(item.getCode());
        }
        return permissions;
    }


    public List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
