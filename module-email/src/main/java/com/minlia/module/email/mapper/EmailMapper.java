package com.minlia.module.email.mapper;

import com.minlia.module.email.entity.EmailRecord;

import java.util.List;

public interface EmailMapper {

    void create(EmailRecord emailRecord);

    void update(EmailRecord emailRecord);

    void delete(String number);

    EmailRecord one(EmailRecord emailRecord);

    List<EmailRecord> list(EmailRecord emailRecord);

}