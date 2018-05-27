package com.minlia.modules.rbac.backend.loginlog.mapper;

import com.minlia.modules.rbac.backend.loginlog.entity.LoginLog;

import java.util.Date;
import java.util.List;

public interface LoginLogMapper {

    long create(LoginLog log);

    long deleteBeforeTime(Date time);

    List<LoginLog> queryList();

}
