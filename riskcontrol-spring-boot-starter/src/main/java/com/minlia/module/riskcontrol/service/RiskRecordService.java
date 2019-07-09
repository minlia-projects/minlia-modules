package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.module.riskcontrol.entity.RiskRecord;
import com.minlia.module.riskcontrol.event.Event;
import com.minlia.module.riskcontrol.repository.RiskEventListRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class RiskRecordService {

    private String channel = this.getClass().getName();

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskEventListRepository riskEventListRepository;

    /**
     * 创建
     *
     * @param event
     */
    public void createEvent(Event event) {
        this.createEvent(event, null);
    }

    /**
     * 创建
     *
     * @param event
     */
    public void createEvent(Event event, String details) {
        RiskRecord riskRecord = mapper.map(event, RiskRecord.class);
        riskRecord.setDetails(details);
        riskRecord.setEventDetails(JSON.toJSONString(event));
        riskEventListRepository.save(riskRecord);
    }

    public RiskRecord queryById(Long id) {
        return riskEventListRepository.findOne(id);
    }

    public List<RiskRecord> queryAll() {
        return riskEventListRepository.findAll();
    }

    public List<RiskRecord> queryAll(RiskRecord riskRecord) {
        return riskEventListRepository.findAll(Example.of(riskRecord));
    }

}
