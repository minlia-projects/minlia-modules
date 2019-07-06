package com.minlia.modules.rebecca.mapper;

import com.minlia.modules.rebecca.bean.domain.LoginLog;

import java.time.LocalDateTime;
import java.util.List;

public interface LoginLogMapper {

    long create(LoginLog log);

    long deleteBeforeTime(LocalDateTime time);

    List<LoginLog> queryList();

}
