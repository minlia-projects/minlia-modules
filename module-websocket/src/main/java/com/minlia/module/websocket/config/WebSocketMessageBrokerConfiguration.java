package com.minlia.module.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by will on 6/21/17.
 * 通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息
 * 此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfiguration extends AbstractWebSocketMessageBrokerConfigurer{

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {//endPoint 注册协议节点,并映射指定的URl
        //注册一个Stomp 协议的endpoint,并指定 SockJS协议
        registry.addEndpoint("/endpointWisely").withSockJS();

        //注册一个名字为"endpointChat" 的endpoint,并指定 SockJS协议。   点对点-用
        registry.addEndpoint("/endpointChat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {//配置消息代理(message broker)
        //广播式应配置一个/topic 消息代理
        registry.enableSimpleBroker("/topic");

        //点对点式增加一个/queue 消息代理
        registry.enableSimpleBroker("/queue","/topic");

//        registry.enableSimpleBroker("/topic");               //设置服务器广播消息的基础路径
//        registry.setApplicationDestinationPrefixes("/app");  //设置客户端订阅消息的基础路径
//        registry.setPathMatcher(new AntPathMatcher("."));    //可以已“.”来分割路径，看看类级别的@messageMapping和方法级别的@messageMapping

    }

}
