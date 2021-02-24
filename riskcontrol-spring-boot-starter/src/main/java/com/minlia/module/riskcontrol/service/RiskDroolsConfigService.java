package com.minlia.module.riskcontrol.service;

import com.minlia.module.riskcontrol.entity.RiskDroolsConfig;
import com.minlia.module.riskcontrol.event.RiskConfigReloadEvent;
import com.minlia.module.riskcontrol.repository.RiskConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RiskDroolsConfigService {

    public static String channel = "risk:drools";

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    private Map<String, RiskDroolsConfig> configMap;

    @Autowired
    private RiskConfigRepository riskConfigRepository;

    @PostConstruct
    public void init() {
        updateCache();
    }

    public void updateCache() {
        List<RiskDroolsConfig> configs = riskConfigRepository.findAllByDisFlag(false);
        configMap = configs.stream().collect(Collectors.toConcurrentMap(RiskDroolsConfig::getRuleKey, Function.identity()));
        log.info("配置缓存更新成功");

        RiskConfigReloadEvent.onReload();
    }

    public void pub(RiskDroolsConfig riskDroolsConfig) {
        riskDroolsConfig.setTime(LocalDateTime.now());
        riskConfigRepository.save(riskDroolsConfig);
        redisTemplate.convertAndSend(channel, "");
    }

    public List<RiskDroolsConfig> getAll() {
        return configMap.values().stream().collect(Collectors.toList());
    }

    public RiskDroolsConfig get(String key) {
        return configMap.get(key);
    }

    public List<RiskDroolsConfig> queryAll(RiskDroolsConfig riskDroolsConfig) {
        List<RiskDroolsConfig> list = riskConfigRepository.findAll(Example.of(riskDroolsConfig));
        return list;
    }

}
