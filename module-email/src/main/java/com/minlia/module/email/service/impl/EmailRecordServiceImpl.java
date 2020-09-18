package com.minlia.module.email.service.impl;

import com.minlia.module.email.entity.EmailRecordEntity;
import com.minlia.module.email.mapper.EmailRecordMapper;
import com.minlia.module.email.service.EmailRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮件记录 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
@Service
public class EmailRecordServiceImpl extends ServiceImpl<EmailRecordMapper, EmailRecordEntity> implements EmailRecordService {

}
