package com.minlia.module.websocket.encoder;


import com.google.gson.Gson;
import com.minlia.module.websocket.body.ResponseMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by garen on 2017/12/1.
 */
public class MessageEncoder implements Encoder.Text<ResponseMessage> {

    @Override
    public String encode(ResponseMessage message) throws EncodeException {
        Gson gson = new Gson();
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageEncoder - init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageEncoder - destroy method called");
    }

}