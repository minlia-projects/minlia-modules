package com.minlia.aliyun.green.annotation;

import com.minlia.aliyun.green.bean.AliyunGreenResult;
import com.minlia.aliyun.green.constant.AliyunGreenCode;
import com.minlia.aliyun.green.service.AliyunGreenService;
import com.minlia.cloud.i18n.Lang;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Annotation that check the bank card number
 *
 * @author garen
 * @date 2017/6/30
 */