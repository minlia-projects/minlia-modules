package com.minlia.modules.security.model;

import com.minlia.cloud.body.Body;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

/**
 * 当前用户上下文
 */
@Data
@ApiModel
public class UserContext implements Body {
    @ApiModelProperty(value = "用户名", example = "test")
    private final String username;


//    @ApiModelProperty(value = "过期时间",example = "2020-12-12 00:00:00")
//    private  Date expireDate;


    @ApiModelProperty(value = "权限点", example = "[{'authority':'user.change.password'}]")
    private final List<GrantedAuthority> authorities;


//    @ApiModelProperty(value="我拥有的菜单项")
//    private List<Navigation> navigations= Lists.newArrayList();


//    private UserContext(String username, List<GrantedAuthority> authorities,Date expireDate) {
//        this.username = username;
//        this.authorities = authorities;
//        this.expireDate = expireDate;
//    }

    private UserContext(String username, List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities, Date expirDate) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username is blank: " + username);
        }
        return new UserContext(username, authorities);
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Username is blank: " + username);
        }
        return new UserContext(username, authorities);
    }

    public String getUsername() {
        return username;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

//    public Date getExpireDate() {
//        return expireDate;
//    }
//
//    public void setExpireDate(Date expireDate) {
//        this.expireDate = expireDate;
//    }
}
