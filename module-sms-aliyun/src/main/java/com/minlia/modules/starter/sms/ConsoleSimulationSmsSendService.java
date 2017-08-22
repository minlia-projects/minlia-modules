package com.minlia.modules.starter.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ConsoleSimulationSmsSendService implements SmsSendService {
    public boolean send(String to, String templateCode, String jsonArguments) {
        log.info("Console simulation sms sent to: {} with template code: {} and arguments {}", to, templateCode, jsonArguments);
        return true;
    }

//    @Override
//    public boolean send(String bibleCode, String bibleItemCode, String to, String jsonArgumentsObject) {
//        log.info("Console simulation sms sent to: {} with arguments {} {} {}", to, bibleCode,bibleItemCode,jsonArgumentsObject);
//        return false;
//    }
}
