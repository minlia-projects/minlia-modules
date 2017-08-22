package com.minlia.modules.starter.sms;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.minlia.modules.starter.sms.properties.AliyunSmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AliyunSmsSendService implements SmsSendService {

    @Autowired
    AliyunSmsProperties value;

//    @Autowired
//    BibleOpenSearchService bibleOpenSearchService;

    @Autowired
    private IAcsClient client;

    public boolean send(String to, String templateCode, String jsonArguments) {
        String signName = value.getSignName();
        try {
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setParamString(jsonArguments);
            request.setRecNum(to);
            SingleSendSmsResponse response = client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public boolean send(String bibleCode, String bibleItemCode, String to, String jsonArgumentsObject) {
//        String smsTemplateCode = bibleOpenSearchService.get(bibleCode, bibleItemCode);
//        return this.send(to,smsTemplateCode,jsonArgumentsObject);
//    }


}
