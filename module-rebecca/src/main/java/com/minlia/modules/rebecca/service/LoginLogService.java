package com.minlia.modules.rebecca.service;

import com.github.pagehelper.PageInfo;
import com.minlia.modules.rebecca.bean.domain.LoginLog;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface LoginLogService {

    void create(LoginLog loginLog);

    long deleteBeforeTime(LocalDateTime date);

    List<LoginLog> queryList();

    PageInfo<LoginLog> queryPage(Pageable pageable);

}
