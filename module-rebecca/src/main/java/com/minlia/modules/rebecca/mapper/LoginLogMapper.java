package com.minlia.modules.rebecca.mapper;

import com.minlia.modules.rebecca.bean.domain.LoginLog;

import java.util.Date;
import java.util.List;

public interface LoginLogMapper {

    long create(LoginLog log);

    long deleteBeforeTime(Date time);

    List<LoginLog> queryList();

}
