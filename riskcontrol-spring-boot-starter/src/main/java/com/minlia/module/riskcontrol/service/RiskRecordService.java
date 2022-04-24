package com.minlia.module.riskcontrol.service;

import com.minlia.cloud.code.Code;
import com.minlia.module.riskcontrol.config.RiskcontrolConfig;
import com.minlia.module.riskcontrol.entity.RiskRecord;
import com.minlia.module.riskcontrol.event.Event;
import com.minlia.module.riskcontrol.repository.RiskRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
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

    private final Mapper mapper;
    private final RiskcontrolConfig riskcontrolConfig;
    private final RiskRecordRepository riskRecordRepository;

    public RiskRecordService(Mapper mapper, RiskcontrolConfig riskcontrolConfig, RiskRecordRepository riskRecordRepository) {
        this.mapper = mapper;
        this.riskcontrolConfig = riskcontrolConfig;
        this.riskRecordRepository = riskRecordRepository;
    }

    /**
     * 创建
     *
     * @param event
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createEvent(Event event) {
        this.createEvent(event, event.getDetails());
    }

    /**
     * 创建
     *
     * @param event
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createEvent(Event event, Code code, Object... args) {
        this.createEvent(event, code.message(args));
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
            riskRecord.setSceneValue(event.getSceneValue());
            riskRecord.setDetails(details);
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
