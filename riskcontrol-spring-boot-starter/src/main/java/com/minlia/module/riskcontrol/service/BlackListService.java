package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.module.riskcontrol.dao.RedisDao;
import com.minlia.module.riskcontrol.entity.BlackList;
import com.minlia.module.riskcontrol.repository.BlackListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class BlackListService {

    private String channel = this.getClass().getName();

    private Map<String, BlackList> blackListMap;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private BlackListRepository blackListRepository;

    @PostConstruct
    public void init() {
        new Thread() {
            @Override
            public void run() {
                redisDao.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String pchannel, String message) {
                        log.info("redis通知，channel={},message={}", pchannel, message);
                        if (channel.equals(pchannel)) {
                            updateCache();
                        }
                    }
                }, channel);
            }
        }.start();
        updateCache();
    }

    public void pub(BlackList blackList) {
        add(blackList);
        redisDao.publish(this.channel, "");
    }

    public List<BlackList> getAll() {
        return blackListMap.values().stream().collect(Collectors.toList());
    }

    public void updateCache() {
        List<BlackList> blackLists = blackListRepository.findAll();
        Map<String, BlackList> tempMap = new ConcurrentHashMap<>();
        for (BlackList blackList : blackLists) {
            tempMap.put(blackList.getValue(), blackList);
        }
        blackListMap = tempMap;
        log.info("黑名单缓存更新成功");
    }

    public void add(BlackList blackList) {
        if (blackListRepository.existsAllByDimensionAndValue(blackList.getDimension(), blackList.getValue())) {
            log.info("黑名单已存在，{}", JSON.toJSONString(blackList));
        } else {
            blackListRepository.save(blackList);
            log.info("黑名单添加成功，{}", JSON.toJSONString(blackList));
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id) {
        blackListRepository.delete(id);
        redisDao.publish(this.channel, "");
    }

    public BlackList queryById(Long id) {
        return blackListRepository.findOne(id);
    }

    public List<BlackList> queryAll() {
        return blackListRepository.findAll();
    }

    /**
     * 是否存在
     * @param dimension
     * @param type
     * @param value
     * @return
     */
    public boolean exists(BlackList.EnumDimension dimension, BlackList.EnumType type, String value) {
        return blackListRepository.exists(Example.of(BlackList.builder().dimension(dimension).type(type).value(value).build()));
    }

    public boolean contain(BlackList.EnumDimension enumDimension, BlackList.EnumType enumType, String value) {
        BlackList blackList1 = blackListMap.get(value);
        return blackList1 == null ? false : (blackList1.getDimension().equals(enumDimension) && blackList1.getType().equals(enumType));
    }

}
