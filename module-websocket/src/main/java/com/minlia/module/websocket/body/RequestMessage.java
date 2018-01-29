package com.minlia.module.websocket.body;

import lombok.Data;

/**
 * Created by garen on 2017/7/20.
 */
@Data
public class RequestMessage {

    private String subject;

    private String content;

}
