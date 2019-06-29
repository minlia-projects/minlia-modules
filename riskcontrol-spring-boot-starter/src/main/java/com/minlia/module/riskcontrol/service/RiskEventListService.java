package com.minlia.module.riskcontrol.service;

import com.minlia.module.riskcontrol.entity.RiskEventList;
import com.minlia.module.riskcontrol.event.Event;
import com.minlia.module.riskcontrol.repository.RiskEventListRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class RiskEventListService {

    private String channel = this.getClass().getName();

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskEventListRepository riskEventListRepository;

    /**
     * 创建
     * @param event
     */
    public void createEvent(Event event) {
        RiskEventList riskEventList = mapper.map(event, RiskEventList.class);
        riskEventListRepository.save(riskEventList);
    }

//    /**
//     * 创建
//     * @param event
//     */
//    public void createRiskEvent(Event event, String ) {
//        if (!riskEventListRepository.exists(Example.of(RiskEventList))) {
//            riskEventListRepository.save(RiskEventList);
//        }
//    }

    /**
     * 更新
     * @param RiskEventList
     */
    public void update(RiskEventList RiskEventList) {
        if (riskEventListRepository.exists(RiskEventList.getId())) {
            riskEventListRepository.save(RiskEventList);
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id) {
        riskEventListRepository.delete(id);
    }

    public RiskEventList queryById(Long id) {
        return riskEventListRepository.findOne(id);
    }

    public List<RiskEventList> queryAll() {
        return riskEventListRepository.findAll();
    }

}
