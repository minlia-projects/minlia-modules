//package com.minlia.module.member.listener;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.minlia.cloud.body.Response;
//import com.minlia.module.member.entity.SysMemberEntity;
//import com.minlia.module.member.event.SysUserAuthEvent;
//import com.minlia.module.member.service.SysMemberService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
///**
// * @author garen
// * @date 2017/7/13
// * 用户认证成功事件
// */
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class SysUserAuthListener {
//
//    private final RestTemplate restTemplate;
//    private final SysMemberService sysMemberService;
//
//    @EventListener
//    public void authed(SysUserAuthEvent event) {
//        Long uid = (Long) event.getSource();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity httpEntity = new HttpEntity<>(null, headers);
//        Response response = restTemplate.postForObject("http://127.0.0.1:10000/api/account/bind/" + uid, httpEntity, Response.class);
//        if (response.isSuccess()) {
//            sysMemberService.update(Wrappers.<SysMemberEntity>lambdaUpdate().eq(SysMemberEntity::getUid, uid).set(SysMemberEntity::getChainAddress, response.getPayload()));
//        }
//    }
//
//}