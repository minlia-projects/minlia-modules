package com.minlia.module.sms.mapper;

import com.minlia.module.sms.entity.SmsRecord;

public interface SmsRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsRecord record);

    int insertSelective(SmsRecord record);

    SmsRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsRecord record);

    int updateByPrimaryKey(SmsRecord record);
}