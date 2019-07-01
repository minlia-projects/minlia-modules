package com.minlia.module.email.service;

import com.minlia.module.email.entity.EmailRecord;

import java.util.List;

public interface EmailRecordService {

    int insertSelective(EmailRecord record);

    int updateByPrimaryKeySelective(EmailRecord record);

    int deleteByPrimaryKey(Long id);

    EmailRecord selectByPrimaryKey(Long id);

    List<EmailRecord> selectByAll(EmailRecord emailRecord);

    EmailRecord selectOneByNumber(String number);
}
