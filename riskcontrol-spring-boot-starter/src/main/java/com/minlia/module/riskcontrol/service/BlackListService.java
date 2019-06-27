package com.minlia.module.riskcontrol.service;

import com.minlia.module.riskcontrol.entity.BlackList;
import com.minlia.module.riskcontrol.mapper.BlackListMapper;
import com.minlia.module.riskcontrol.repository.BlackListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
    private BlackListMapper blackListMapper;

    @Autowired
    private BlackListRepository blackListRepository;

    public List<BlackList> getAll() {
        return blackListMap.values().stream().collect(Collectors.toList());
    }

    public void updateCache() {
        List<BlackList> blackLists = blackListMapper.queryAll();
        Map<String, BlackList> tempMap = new ConcurrentHashMap<>();
        for (BlackList blackList : blackLists) {
            tempMap.put(blackList.getValue(), blackList);
        }
        blackListMap = tempMap;
        log.info("黑名单缓存更新成功");
    }

    /**
     * 创建
     * @param blackList
     */
    public void save(BlackList blackList) {
        if (!blackListRepository.exists(Example.of(blackList))) {
            blackListRepository.save(blackList);
        }
    }

    /**
     * 更新
     * @param blackList
     */
    public void update(BlackList blackList) {
        if (blackListRepository.exists(blackList.getId())) {
            blackListRepository.save(blackList);
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id) {
        blackListRepository.delete(id);
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

}
