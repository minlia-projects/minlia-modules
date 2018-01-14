package com.minlia.modules.starter.sms;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ConsoleSimulationSmsSendService implements SmsSendService {

    public StatefulBody send(String to, String templateCode, String jsonArguments) {
        log.info("Console simulation sms sent to: {} with template code: {} and arguments {}", to, templateCode, jsonArguments);
        return SuccessResponseBody.builder().build();
    }

}
