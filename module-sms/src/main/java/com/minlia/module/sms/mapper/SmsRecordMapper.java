package com.minlia.module.sms.mapper;

import com.minlia.module.sms.entity.SmsRecord;
import com.minlia.module.sms.ro.SmsRecordQRO;

import java.util.List;

public interface SmsRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsRecord record);

    int insertSelective(SmsRecord record);

    SmsRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsRecord record);

    int updateByPrimaryKey(SmsRecord record);

    List<SmsRecord> selectByAll(SmsRecordQRO smsRecord);



}