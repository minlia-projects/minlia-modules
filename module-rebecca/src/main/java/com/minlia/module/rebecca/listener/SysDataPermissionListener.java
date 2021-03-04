package com.minlia.module.rebecca.listener;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.module.data.constant.SysDataConstants;
import com.minlia.module.rebecca.entity.SysDataPermissionEntity;
import com.minlia.module.rebecca.service.SysDataPermissionService;
import com.minlia.module.redis.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SysDataPermissionListener implements CommandLineRunner {

    private final SysDataPermissionService sysDataPermissionService;

    @Override
    public void run(String... args) throws Exception {
        //获取所有有效的
        List<String> methods = sysDataPermissionService.list(Wrappers.<SysDataPermissionEntity>lambdaQuery().select(SysDataPermissionEntity::getMethod).eq(SysDataPermissionEntity::getDisFlag, false))
                .stream().map(SysDataPermissionEntity::getMethod).collect(Collectors.toList());
        //添加到缓存
        if (CollectionUtils.isNotEmpty(methods)) {
            RedisUtils.sSet(SysDataConstants.Redis.DATA_PERMISSION_PREFIX, methods);
        }
    }

}