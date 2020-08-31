package com.minlia.module.approved.email.service.impl;

import com.minlia.module.approved.email.entity.EmailRecord;
import com.minlia.module.approved.email.entity.Richtext;
import com.minlia.module.approved.email.enumeration.LocaleEnum;
import com.minlia.module.approved.email.mapper.ApprovedEmailRecordMapper;
import com.minlia.module.approved.email.ro.EmailRecordQRO;
import com.minlia.module.approved.email.service.ApprovedEmailRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApprovedEmailRecordServiceImpl implements ApprovedEmailRecordService {

    @Resource
    private ApprovedEmailRecordMapper approvedEmailRecordMapper;

    @Override
    public int insertSelective(EmailRecord record) {
        return approvedEmailRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(EmailRecord record) {
        return approvedEmailRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return approvedEmailRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EmailRecord selectByPrimaryKey(Long id) {
        return approvedEmailRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<EmailRecord> selectByAll(EmailRecordQRO emailRecord) {
        return approvedEmailRecordMapper.selectByAll(emailRecord);
    }

    @Override
    public EmailRecord selectOneByNumber(String number) {
        return approvedEmailRecordMapper.selectOneByNumber(number);
    }

    @Override
    public Richtext queryRichtextByTypeAndCode(String type, String code, LocaleEnum locale) {
        return approvedEmailRecordMapper.queryRichtextByTypeAndCode(type,code,locale);
    }

    @Override
    public Boolean queryRealSwitchFlag() {
        return approvedEmailRecordMapper.queryRealSwitchFlag();
    }
}
