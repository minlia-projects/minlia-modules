package com.minlia.module.wechat.material.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by garen on 2018/6/29.
 */
@Component
public class MpMaterialTask {

    @Autowired
    private MpMaterialService mpMaterialService;

    @Scheduled(cron = "1 1 1 * * ?")
    public void clearRedis(){
        mpMaterialService.init("news",0,100);
    }

}