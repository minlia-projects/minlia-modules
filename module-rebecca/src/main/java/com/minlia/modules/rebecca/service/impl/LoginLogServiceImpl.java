package com.minlia.modules.rebecca.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.modules.rebecca.bean.domain.LoginLog;
import com.minlia.modules.rebecca.mapper.LoginLogMapper;
import com.minlia.modules.rebecca.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by garen on 2018/10/24.
 * 角色服务实现
 */
@Service
@Slf4j
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Async
    public void create(LoginLog loginLog) {
        loginLogMapper.create(loginLog);
    }

    @Override
    public long deleteBeforeTime(LocalDateTime date) {
        return loginLogMapper.deleteBeforeTime(date);
    }

    @Override
    public List<LoginLog> queryList() {
        return loginLogMapper.queryList();
    }

    @Override
    public PageInfo<LoginLog> queryPage(Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> loginLogMapper.queryList());
    }

}
