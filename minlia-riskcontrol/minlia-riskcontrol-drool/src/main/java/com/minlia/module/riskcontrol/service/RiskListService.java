package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.module.riskcontrol.entity.RiskList;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.event.RiskListDeleteEvent;
import com.minlia.module.riskcontrol.event.RiskListPublishEvent;
import com.minlia.module.riskcontrol.repository.RiskListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风控名单
 *
 * @author garen
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RiskListService {

    private final RiskListRepository riskListRepository;

    public void save(RiskList riskList) {
        if (this.exists(riskList.getType(), riskList.getDimension(), riskList.getValue())) {
            log.info("黑名单已存在，{}", JSON.toJSONString(riskList));
        } else {
            riskListRepository.save(riskList);
            log.info("黑名单添加成功，{}", JSON.toJSONString(riskList));
        }
        RiskListPublishEvent.execute(riskList);
    }

    public void delete(Long id) {
        RiskList riskList = riskListRepository.getOne(id);
        riskListRepository.deleteById(id);
        RiskListDeleteEvent.execute(riskList);
    }

    public boolean exists(RiskTypeEnum type, RiskList.EnumDimension dimension, String value) {
        return riskListRepository.exists(Example.of(RiskList.builder().type(type).dimension(dimension).value(value).build()));
    }

    public List<RiskList> findByTypeAndDimension(RiskTypeEnum type, RiskList.EnumDimension dimension) {
        return riskListRepository.findAll(Example.of(RiskList.builder().type(type).dimension(dimension).build()));
    }

    public RiskList findById(Long id) {
        return riskListRepository.findById(id).get();
    }

    public List<RiskList> findAll() {
        return riskListRepository.findAll();
    }

    public List<RiskList> findAll(RiskList riskList) {
        return riskListRepository.findAll(Example.of(riskList));
    }

}