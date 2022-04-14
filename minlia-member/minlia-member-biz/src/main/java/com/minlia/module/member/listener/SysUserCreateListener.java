package com.minlia.module.member.listener;

import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.event.SysUserCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author garen
 * @date 2017/7/13
 * 用户创建成功事件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysUserCreateListener {

    private final RestTemplate restTemplate;
    private final SysMemberService sysMemberService;

    @EventListener
    public void authed(SysUserCreateEvent event) {
        SysUserEntity sysUserEntity = (SysUserEntity) event.getSource();
        sysMemberService.create(sysUserEntity);
    }

}