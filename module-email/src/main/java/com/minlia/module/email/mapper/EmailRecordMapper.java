package com.minlia.module.email.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Date;

import com.minlia.module.email.entity.EmailRecord;

public interface EmailRecordMapper {

    int insertSelective(EmailRecord record);

    int updateByPrimaryKeySelective(EmailRecord record);

    int deleteByPrimaryKey(Long id);

    EmailRecord selectByPrimaryKey(Long id);

    List<EmailRecord> selectByAll(EmailRecord emailRecord);

    EmailRecord selectOneByNumber(@Param("number")String number);

}