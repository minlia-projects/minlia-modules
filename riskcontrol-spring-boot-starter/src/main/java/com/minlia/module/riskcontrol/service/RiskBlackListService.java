package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.module.riskcontrol.entity.RiskBlackList;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.event.RiskBlacklistReloadEvent;
import com.minlia.module.riskcontrol.repository.RiskBlackListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RiskBlackListService {

    public static String channel = "risk:blacklist";
    private Map<String, RiskBlackList> blackListMap;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;
    @Autowired
    private RiskBlackListRepository riskBlackListRepository;

    @PostConstruct
    public void init() {
        updateCache();
    }

    public void pub(RiskBlackList riskBlackList) {
        add(riskBlackList);
        redisTemplate.convertAndSend(channel, "");
    }

    public List<RiskBlackList> getAll() {
        return blackListMap.values().stream().collect(Collectors.toList());
    }

    public void updateCache() {
        List<RiskBlackList> riskBlackLists = riskBlackListRepository.findAll();
        blackListMap = riskBlackLists.stream().collect(Collectors.toConcurrentMap(RiskBlackList::getValue, Function.identity()));
        log.info("黑名单缓存更新成功");

        RiskBlacklistReloadEvent.onReload();
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
        riskBlackListRepository.deleteById(id);
        redisTemplate.convertAndSend(channel, "");
    }

    public RiskBlackList queryById(Long id) {
        return riskBlackListRepository.findById(id).get();
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
    public boolean exists(RiskBlackList.EnumDimension dimension, RiskTypeEnum type, String value) {
        return riskBlackListRepository.exists(Example.of(RiskBlackList.builder().dimension(dimension).type(type).value(value).build()));
    }

    public boolean contain(RiskBlackList.EnumDimension enumDimension, RiskTypeEnum enumType, String value) {
        RiskBlackList riskBlackList = blackListMap.get(value);
        return riskBlackList == null ? false : (riskBlackList.getDimension().equals(enumDimension) && riskBlackList.getType().equals(enumType));
    }

}
