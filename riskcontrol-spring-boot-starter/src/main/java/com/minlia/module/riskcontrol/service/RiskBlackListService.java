package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.module.riskcontrol.dao.RedisDao;
import com.minlia.module.riskcontrol.entity.RiskBlackList;
import com.minlia.module.riskcontrol.repository.RiskBlackListRepository;
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
public class RiskBlackListService {

    private String channel = this.getClass().getName();

    private Map<String, RiskBlackList> blackListMap;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private RiskBlackListRepository riskBlackListRepository;

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

    public void pub(RiskBlackList riskBlackList) {
        add(riskBlackList);
        redisDao.publish(this.channel, "");
    }

    public List<RiskBlackList> getAll() {
        return blackListMap.values().stream().collect(Collectors.toList());
    }

    public void updateCache() {
        List<RiskBlackList> riskBlackLists = riskBlackListRepository.findAll();
        Map<String, RiskBlackList> tempMap = new ConcurrentHashMap<>();
        for (RiskBlackList riskBlackList : riskBlackLists) {
            tempMap.put(riskBlackList.getValue(), riskBlackList);
        }
        blackListMap = tempMap;
        log.info("黑名单缓存更新成功");
    }

    public void add(RiskBlackList riskBlackList) {
        if (riskBlackListRepository.existsAllByDimensionAndValue(riskBlackList.getDimension(), riskBlackList.getValue())) {
            log.info("黑名单已存在，{}", JSON.toJSONString(riskBlackList));
        } else {
            riskBlackListRepository.save(riskBlackList);
            log.info("黑名单添加成功，{}", JSON.toJSONString(riskBlackList));
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        riskBlackListRepository.delete(id);
        redisDao.publish(this.channel, "");
    }

    public RiskBlackList queryById(Long id) {
        return riskBlackListRepository.findOne(id);
    }

    public List<RiskBlackList> queryAll() {
        return riskBlackListRepository.findAll();
    }

    public List<RiskBlackList> queryAll(RiskBlackList riskBlackList) {
        return riskBlackListRepository.findAll(Example.of(riskBlackList));
    }

    /**
     * 是否存在
     *
     * @param dimension
     * @param type
     * @param value
     * @return
     */
    public boolean exists(RiskBlackList.EnumDimension dimension, RiskBlackList.EnumType type, String value) {
        return riskBlackListRepository.exists(Example.of(RiskBlackList.builder().dimension(dimension).type(type).value(value).build()));
    }

    public boolean contain(RiskBlackList.EnumDimension enumDimension, RiskBlackList.EnumType enumType, String value) {
        RiskBlackList riskBlackList1 = blackListMap.get(value);
        return riskBlackList1 == null ? false : (riskBlackList1.getDimension().equals(enumDimension) && riskBlackList1.getType().equals(enumType));
    }

}