package com.minlia.module.email.service.impl;

import com.minlia.module.email.entity.EmailRecord;
import com.minlia.module.email.mapper.EmailRecordMapper;
import com.minlia.module.email.ro.EmailRecordQRO;
import com.minlia.module.email.service.EmailRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmailRecordServiceImpl implements EmailRecordService {

    @Resource
    private EmailRecordMapper emailRecordMapper;

    @Override
    public int insertSelective(EmailRecord record) {
        return emailRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(EmailRecord record) {
        return emailRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return emailRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EmailRecord selectByPrimaryKey(Long id) {
        return emailRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<EmailRecord> selectByAll(EmailRecordQRO emailRecord) {
        return emailRecordMapper.selectByAll(emailRecord);
    }

    @Override
    public EmailRecord selectOneByNumber(String number) {
        return emailRecordMapper.selectOneByNumber(number);
    }
}
