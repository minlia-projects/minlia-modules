package com.minlia.module.approved.email.service;

import com.minlia.module.approved.email.entity.EmailRecord;
import com.minlia.module.approved.email.entity.Richtext;
import com.minlia.module.approved.email.enumeration.LocaleEnum;
import com.minlia.module.approved.email.ro.EmailRecordQRO;

import java.util.List;

public interface ApprovedEmailRecordService {

    int insertSelective(EmailRecord record);

    int updateByPrimaryKeySelective(EmailRecord record);

    int deleteByPrimaryKey(Long id);

    EmailRecord selectByPrimaryKey(Long id);

    List<EmailRecord> selectByAll(EmailRecordQRO emailRecord);

    EmailRecord selectOneByNumber(String number);

    Richtext queryRichtextByTypeAndCode(String type, String code, LocaleEnum locale);

    Boolean queryRealSwitchFlag();
}
