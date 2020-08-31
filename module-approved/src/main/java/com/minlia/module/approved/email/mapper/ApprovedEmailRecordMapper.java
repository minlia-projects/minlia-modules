package com.minlia.module.approved.email.mapper;

import com.minlia.module.approved.email.entity.EmailRecord;
import com.minlia.module.approved.email.entity.Richtext;
import com.minlia.module.approved.email.enumeration.LocaleEnum;
import com.minlia.module.approved.email.ro.EmailRecordQRO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApprovedEmailRecordMapper {

    int insertSelective(EmailRecord record);

    int updateByPrimaryKeySelective(EmailRecord record);

    int deleteByPrimaryKey(Long id);

    EmailRecord selectByPrimaryKey(Long id);

    List<EmailRecord> selectByAll(EmailRecordQRO emailRecord);

    EmailRecord selectOneByNumber(@Param("number") String number);

    Richtext queryRichtextByTypeAndCode(@Param("type")String type, @Param("code")String code, @Param("locale")LocaleEnum locale);

    Boolean queryRealSwitchFlag();
}