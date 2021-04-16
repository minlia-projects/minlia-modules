package com.minlia.module.wechat.miniapp.controller;


import com.minlia.cloud.constant.ApiPrefix;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信小程序信息 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
@Api(tags = "Wechat Miniapp User", description = "微信-小程序-用户")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.V1 + "auth")
@RequiredArgsConstructor
public class WxMaUserInfoController {

}