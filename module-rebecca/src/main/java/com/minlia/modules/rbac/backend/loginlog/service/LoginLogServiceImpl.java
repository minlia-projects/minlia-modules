package com.minlia.modules.rbac.backend.loginlog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.backend.loginlog.entity.LoginLog;
import com.minlia.modules.rbac.backend.loginlog.mapper.LoginLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by will on 6/19/17.
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
    public long deleteBeforeTime(Date date) {
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
