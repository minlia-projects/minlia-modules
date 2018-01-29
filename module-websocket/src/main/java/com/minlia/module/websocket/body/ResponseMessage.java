package com.minlia.module.websocket.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> {

    private String code;

    private String message;

    private T payload;

}
