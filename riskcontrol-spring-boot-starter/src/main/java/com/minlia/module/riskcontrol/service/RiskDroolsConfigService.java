package com.minlia.module.riskcontrol.service;

import com.google.common.collect.Lists;
import com.minlia.module.riskcontrol.dao.RedisDao;
import com.minlia.module.riskcontrol.entity.RiskDroolsConfig;
import com.minlia.module.riskcontrol.repository.RiskConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RiskDroolsConfigService {

    private String channel = this.getClass().getName();

    private Map<String, RiskDroolsConfig> configMap;

    @Autowired
    private RiskConfigRepository riskConfigRepository;

    @Autowired
    private RedisDao redisDao;

    @PostConstruct
    public void init() {
        new Thread(() ->
                redisDao.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String pchannel, String message) {
                        log.info("redis通知，channel={},message={}", pchannel, message);
                        if (channel.equals(pchannel)) {
                            updateCache();
                        }
                    }
                }, channel)
        ).start();
        updateCache();
    }

    public void updateCache() {
        List<RiskDroolsConfig> configs = riskConfigRepository.findAllByDisFlag(false);
        configMap = configs.stream().collect(Collectors.toConcurrentMap(RiskDroolsConfig::getRuleKey, Function.identity()));
        log.info("配置缓存更新成功");
    }

    public void pub(RiskDroolsConfig riskDroolsConfig) {
        riskDroolsConfig.setTime(LocalDateTime.now());
        riskConfigRepository.save(riskDroolsConfig);
        redisDao.publish(this.channel, "");
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
