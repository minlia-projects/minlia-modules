package com.minlia.module.riskcontrol.service;

import com.alibaba.fastjson.JSON;
import com.minlia.cloud.code.Code;
import com.minlia.module.riskcontrol.bean.RiskRecordQRO;
import com.minlia.module.riskcontrol.config.RiskcontrolConfig;
import com.minlia.module.riskcontrol.entity.RiskRecord;
import com.minlia.module.riskcontrol.event.Event;
import com.minlia.module.riskcontrol.mapper.RiskRecordMapper;
import com.minlia.module.riskcontrol.repository.RiskRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class RiskRecordService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskcontrolConfig riskcontrolConfig;

    @Autowired
    private RiskRecordMapper riskRecordMapper;

    @Autowired
    private RiskRecordRepository riskRecordRepository;

    /**
     * 创建
     *
     * @param event
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createEvent(Event event) {
        this.createEvent(event, null);
    }

    /**
     * 创建
     *
     * @param event
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createEvent(Event event, String details) {
        if (riskcontrolConfig.getRealSwitchFlag()) {
            RiskRecord riskRecord = mapper.map(event, RiskRecord.class);
            riskRecord.setDetails(details);
            riskRecord.setEventDetails(JSON.toJSONString(event));
            riskRecordRepository.save(riskRecord);
        }
    }

    /**
     * 创建
     *
     * @param event
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createEvent(Event event, Code code, Object... args) {
        if (riskcontrolConfig.getRealSwitchFlag()) {
            RiskRecord riskRecord = mapper.map(event, RiskRecord.class);
            riskRecord.setDetails(code.message(args));
            riskRecord.setEventDetails(JSON.toJSONString(event));
            riskRecordRepository.save(riskRecord);
        }
    }

    public RiskRecord queryById(Long id) {
        return riskRecordRepository.findById(id).get();
    }

    public List<RiskRecord> queryAll() {
        return riskRecordRepository.findAll();
    }

    public List<RiskRecord> queryAll(RiskRecord riskRecord) {
        return riskRecordRepository.findAll(Example.of(riskRecord));
    }

//    public PageInfo<RiskRecord> queryPage(RiskRecordQRO qro) {
//        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskRecordMapper.selectByAll(qro));
//        return pageInfo;
//    }

}
