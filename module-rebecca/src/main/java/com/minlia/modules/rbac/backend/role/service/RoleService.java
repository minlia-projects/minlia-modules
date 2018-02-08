package com.minlia.modules.rbac.backend.role.service;

import com.minlia.modules.rbac.backend.role.body.RoleCreateRequestBody;
import com.minlia.modules.rbac.backend.role.body.RoleUpdateRequestBody;
import com.minlia.modules.rbac.backend.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 6/19/17.
 * 角色接口
 */
public interface RoleService {

    /**
     * 创建
     * @param body
     * @return
     */
    Role create(RoleCreateRequestBody body);

    /**
     * 修改
     * @param body
     * @return
     */
    Role update(RoleUpdateRequestBody body);

    /**
     * 删除
     * @param code
     */
    void delete(String code);

    /**
     * 授予权限点
     * 1、根据角色删除关系表
     * 2、插入新的记录
     * @param code
     * @param permissions
     * @return
     */
    void grantPermission(String code, List<Long> permissions);

    Boolean exists(Long id);

    Boolean exists(String code);

    Role queryById(Long id);

    Role queryByCode(String code);

    List<Role> queryByGuid(String guid);

    List<String> queryCodeByUserId(Long userId);

    List<Long> queryIdByUserId(Long userId);

    List<Role> queryList();

    Page<Role> queryPage(Pageable pageable);
}
