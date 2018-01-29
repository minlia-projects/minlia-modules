package com.minlia.module.websocket.encoder;

import com.google.gson.Gson;
import com.minlia.module.websocket.body.RequestMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by garen on 2017/12/1.
 */
public class MessageDecoder implements Decoder.Text<RequestMessage> {

    @Override
    public RequestMessage decode(String jsonMessage) throws DecodeException {
        Gson gson = new Gson();
        return gson.fromJson(jsonMessage,RequestMessage.class);
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            new Gson().fromJson(jsonMessage,RequestMessage.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("MessageDecoder -init method called");
    }

    @Override
    public void destroy() {
        System.out.println("MessageDecoder - destroy method called");
    }

}