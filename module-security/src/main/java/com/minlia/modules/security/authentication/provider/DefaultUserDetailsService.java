package com.minlia.modules.security.authentication.provider;

import com.minlia.modules.security.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService实现类
 *
 * @author garen
 * @version 1.0.0
 * @createDate 2019-08-02
 */
@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟查询数据库，获取属于Admin和Normal角色的用户
        SysUser user = new SysUser(username, passwordEncoder.encode("Ab123456"), true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("Admin,Normal"));
        user.setGuid("xxxxx");
        return user;
    }

}