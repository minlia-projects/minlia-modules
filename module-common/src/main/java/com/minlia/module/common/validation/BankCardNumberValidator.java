package com.minlia.module.common.validation;

import com.minlia.module.common.bean.response.AlipayBankCardValidateResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Annotation that check the bank card number
 * Created by garen on 2017/6/30.
 */
@Component
public class BankCardNumberValidator implements ConstraintValidator<BankCardNumber, String> {

    @Override
    public void initialize(BankCardNumber bankCardNumber) {

    }

    @Autowired
    RestTemplate restTemplate;

    private final String ALIPAY_VALIDATE_URL = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=%s&cardBinCheck=true";

    @Override
    public boolean isValid(String bankCardNumber, ConstraintValidatorContext ctx) {
        if (StringUtils.isNotEmpty(bankCardNumber)) {
            AlipayBankCardValidateResponse response = restTemplate.getForObject(String.format(ALIPAY_VALIDATE_URL, bankCardNumber), AlipayBankCardValidateResponse.class);
            return response.getValidated();
        } else {
            return true;
        }
    }

}
