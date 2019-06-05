package com.minlia.module.sms.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.minlia.module.sms.entity.SmsRecord;
import com.minlia.module.sms.mapper.SmsRecordMapper;
import com.minlia.module.sms.service.SmsRecordService;

@Service
public class SmsRecordServiceImpl implements SmsRecordService {

    @Resource
    private SmsRecordMapper smsRecordMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return smsRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SmsRecord record) {
        return smsRecordMapper.insert(record);
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
    public int updateByPrimaryKey(SmsRecord record) {
        return smsRecordMapper.updateByPrimaryKey(record);
    }

}

