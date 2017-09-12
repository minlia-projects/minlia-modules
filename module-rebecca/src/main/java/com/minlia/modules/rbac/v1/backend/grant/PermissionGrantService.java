package com.minlia.modules.rbac.v1.backend.grant;

import com.minlia.cloud.body.WithIdItemBody;
import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

/**
 * Created by will on 6/19/17.
 * 授权服务
 */
public interface PermissionGrantService {


    public static final String ENTITY = "permission";

    public static final String ENTITY_READ = ENTITY + ".read";
    public static final String ENTITY_GRANT = ENTITY + ".grant";

    /**
     * 查找所有角色
     *
     * @param pageable
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+ENTITY_READ+"')")
//    public Page<Role> findAllRoles(Pageable pageable);

    /**
     * 查找所有权限点
     *
     * @param pageable
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+ENTITY_READ+"')")
    public Page<Permission> findAllPermissions(Pageable pageable);


    /**
     * 查找指定角色已拥有的权限点
     *
     * @param roleId
     * @param pageable
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+ENTITY_READ+"')")
    public Page<Permission> findPermissionsByRoleId(Long roleId, Pageable pageable);


    /**
     * 查找指定用户已拥有的角色
     * @param userId
     * @param pageable
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+ RoleService.ENTITY_READ+"')")
    Page<Role> findRoleByUserId(Long userId, Pageable pageable);


    /**
     * 给指定角色授权权限点
     * @param roleId
     * @param body
     * @return
     */
//    @PreAuthorize(value = "hasAnyAuthority('"+ENTITY_GRANT+"')")
    Set<Permission> grantPermissionsByRoleId(Long roleId, WithIdItemBody body);
}
