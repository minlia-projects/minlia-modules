package com.minlia.modules.rbac.backend.loginlog.service;

import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.backend.loginlog.entity.LoginLog;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface LoginLogService {

    void create(LoginLog loginLog);

    long deleteBeforeTime(Date date);

    List<LoginLog> queryList();

    PageInfo<LoginLog> queryPage(Pageable pageable);

}
