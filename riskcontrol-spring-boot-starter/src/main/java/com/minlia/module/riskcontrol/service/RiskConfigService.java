package com.minlia.module.riskcontrol.service;

import com.minlia.module.riskcontrol.dao.RedisDao;
import com.minlia.module.riskcontrol.entity.RiskConfig;
import com.minlia.module.riskcontrol.repository.RiskConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class RiskConfigService {

    private String channel = this.getClass().getName();

    private Map<String, RiskConfig> configMap;

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
        List<RiskConfig> configs = riskConfigRepository.findAll();
        Map<String, RiskConfig> tempMap = new ConcurrentHashMap<>();
        for (RiskConfig config : configs) {
            tempMap.put(config.getKey(), config);
        }
        configMap = tempMap;
        log.info("配置缓存更新成功");
    }

    public void save(RiskConfig riskConfig) {
        riskConfigRepository.save(riskConfig);
        redisDao.publish(this.channel, "");
    }

    public List<RiskConfig> queryAll() {
        return configMap.values().stream().collect(Collectors.toList());
    }

    public RiskConfig get(String key) {
        return configMap.get(key);
    }

}
