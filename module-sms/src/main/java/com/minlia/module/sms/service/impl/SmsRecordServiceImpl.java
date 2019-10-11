package com.minlia.module.sms.service.impl;

import com.minlia.module.sms.ro.SmsRecordQRO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.minlia.module.sms.entity.SmsRecord;
import com.minlia.module.sms.mapper.SmsRecordMapper;
import com.minlia.module.sms.service.SmsRecordService;

import java.util.List;

@Service
public class SmsRecordServiceImpl implements SmsRecordService {

    @Resource
    private SmsRecordMapper smsRecordMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return smsRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(SmsRecord record) {
        return smsRecordMapper.insertSelective(record);
    }

    @Override
    public SmsRecord selectByPrimaryKey(Integer id) {
        return smsRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SmsRecord record) {
        return smsRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SmsRecord> selectByAll(SmsRecordQRO smsRecord) {
        return smsRecordMapper.selectByAll(smsRecord);
    }
}

