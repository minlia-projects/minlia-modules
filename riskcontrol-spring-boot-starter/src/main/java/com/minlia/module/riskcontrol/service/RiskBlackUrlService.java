package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.module.riskcontrol.dao.RedisDao;
import com.minlia.module.riskcontrol.entity.RiskBlackUrl;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.repository.RiskBlackUrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RiskBlackUrlService {

    private String channel = this.getClass().getName();

    private Map<String, RiskBlackUrl> blackListMap;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private RiskBlackUrlRepository riskBlackUrlRepository;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            redisDao.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String pchannel, String message) {
                    log.info("redis通知，channel={},message={}", pchannel, message);
                    if (channel.equals(pchannel)) {
                        updateCache();
                    }
                }
            }, channel);
        }).start();
        updateCache();
    }

    public void pub(RiskBlackUrl riskBlackUrl) {
        add(riskBlackUrl);
        redisDao.publish(this.channel, "");
    }

    public List<RiskBlackUrl> getAll() {
        return blackListMap.values().stream().collect(Collectors.toList());
    }

    public void updateCache() {
        List<RiskBlackUrl> riskBlackUrls = riskBlackUrlRepository.findAll();
        Map<String, RiskBlackUrl> tempMap = new ConcurrentHashMap<>();
        for (RiskBlackUrl riskBlackUrl : riskBlackUrls) {
            tempMap.put(riskBlackUrl.getValue(), riskBlackUrl);
        }
        blackListMap = tempMap;
        log.info("黑名单缓存更新成功");
    }

    public void add(RiskBlackUrl riskBlackUrl) {
        if (riskBlackUrlRepository.existsAllByTypeAndValue(riskBlackUrl.getType(), riskBlackUrl.getValue())) {
            log.info("黑名单已存在，{}", JSON.toJSONString(riskBlackUrl));
        } else {
            riskBlackUrlRepository.save(riskBlackUrl);
            log.info("黑名单添加成功，{}", JSON.toJSONString(riskBlackUrl));
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        riskBlackUrlRepository.delete(id);
        redisDao.publish(this.channel, "");
    }

    public RiskBlackUrl queryById(Long id) {
        return riskBlackUrlRepository.findOne(id);
    }

    public List<RiskBlackUrl> queryAll() {
        return riskBlackUrlRepository.findAll();
    }

    public List<RiskBlackUrl> queryAll(RiskBlackUrl riskBlackUrl) {
        return riskBlackUrlRepository.findAll(Example.of(riskBlackUrl));
    }

    /**
     * 是否存在
     *
     * @param type
     * @param value
     * @return
     */
    public boolean exists(RiskTypeEnum type, String value) {
        return riskBlackUrlRepository.exists(Example.of(RiskBlackUrl.builder().type(type).type(type).value(value).build()));
    }

    public boolean contain(RiskTypeEnum enumType, String value) {
        RiskBlackUrl riskBlackUrl = blackListMap.get(value);
        if (null != riskBlackUrl) {
            return riskBlackUrl.getType().equals(enumType);
        } else {
            for (Map.Entry<String, RiskBlackUrl> entry : blackListMap.entrySet()) {
                if (value.indexOf(entry.getKey()) == 0) {
                    return entry.getValue().getType().equals(enumType);
                }
            }
        }
        return false;
    }

}
