package com.minlia.module.currency.scheduled;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.module.currency.bean.SysCurrencyRateDto;
import com.minlia.module.currency.config.CurrencyRateConfig;
import com.minlia.module.currency.entity.SysCurrencyRateEntity;
import com.minlia.module.currency.service.SysCurrencyRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CurrencyRateScheduled {

    //    public final static String url = "http://api.k780.com/?app=finance.rate_unionpayintl&cur_base=CNY&cur_transaction=USD&cur_date=20170501&appkey=56785&sign=35ecce06a86350a5f7d6009fc8203ce6&format=json";
    public final static String URL = "http://api.k780.com/?app=finance.rate_unionpayintl&cur_base={1}&cur_transaction={2}&cur_date={3}&appkey={4}&sign={5}&format=json";

    private final RestTemplate restTemplate;
    private final CurrencyRateConfig currencyRateConfig;
    private final SysCurrencyRateService sysCurrencyRateService;

    public CurrencyRateScheduled(SysCurrencyRateService sysCurrencyRateService, RestTemplate restTemplate, CurrencyRateConfig currencyRateConfig) {
        this.restTemplate = restTemplate;
        this.currencyRateConfig = currencyRateConfig;
        this.sysCurrencyRateService = sysCurrencyRateService;
    }

    /**
     * 工作日每天17：20/ShippingAddressServiceImpl.java
     */
    //    @Scheduled(cron = "0 20 17 ? * 2-6")
    @Scheduled(cron = "0 20 17 ? * MON-FRI")
//    @Scheduled(cron = "0/1 * * ? * MON-FRI")
    public void autoUpdate() {
        List<SysCurrencyRateEntity> currencyRateEntities = sysCurrencyRateService.list(Wrappers.<SysCurrencyRateEntity>lambdaQuery().eq(SysCurrencyRateEntity::getAutoFlag, true));
        String curDate = LocalDate.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATE_PATTERN));
        for (SysCurrencyRateEntity currencyRateEntity : currencyRateEntities) {
            SysCurrencyRateDto rateDto = restTemplate.getForObject(URL, SysCurrencyRateDto.class, currencyRateEntity.getCurBase(), currencyRateEntity.getCurTrans(), curDate, currencyRateConfig.getAppKey(), currencyRateConfig.getSign());
            if (rateDto.isSuccess()) {
                currencyRateEntity.setRate(new BigDecimal(rateDto.getResult().getExchangeRate()));
                currencyRateEntity.setTime(LocalDate.parse(rateDto.getResult().getCurDate()));
                sysCurrencyRateService.updateById(currencyRateEntity);
            }
        }
    }

}