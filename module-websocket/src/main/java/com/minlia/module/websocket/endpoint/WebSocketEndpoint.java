package com.minlia.module.websocket.endpoint;

import com.minlia.module.websocket.body.RequestMessage;
import com.minlia.module.websocket.body.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class WebSocketEndpoint {

//    @RequestMapping(value = "/login")
//    public String login(){
//        return  "login";
//    }
//
//    @RequestMapping(value = "/ws")
//    public String ws(){
//        return  "ws";
//    }
//
//    @RequestMapping(value = "/chat")
//    public String chat(){
//        return  "chat";
//    }

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(@RequestBody RequestMessage message) {
        System.out.println(message.getContent());
        return ResponseMessage.builder().message("welcome," + message.getContent() + " !").build();
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
    public void handlerChat(Principal principal, String message) {
        /**
         * 此处是一段硬编码。如果发送人是wyf 则发送给 wisely 如果发送人是wisely 就发送给 wyf。
         * 通过当前用户,然后查找消息,如果查找到未读消息,则发送给当前用户。
         */


        if (principal.getName().equals("admin")) {
            //通过convertAndSendToUser 向用户发送信息,
            // 第一个参数是接收消息的用户,第二个参数是浏览器订阅的地址,第三个参数是消息本身
            messagingTemplate.convertAndSendToUser("admin",
                    "/queue/notifications", principal.getName() + "-send:"
                            + message);
            /**
             * 72 行操作相等于
             * messagingTemplate.convertAndSend("/user/abel/queue/notifications",principal.getName() + "-send:"
             + message.getName());
             */
        } else {
            messagingTemplate.convertAndSendToUser("admin",
                    "/queue/notifications", principal.getName() + "-send:"
                            + message);
        }
    }

}