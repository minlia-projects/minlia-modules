package com.minlia.module.websocket.endpoint;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by garen on 2017/11/30.
 */
@ServerEndpoint("/websocket")
@Component
public class MySocketEndpoint {

    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<MySocketEndpoint> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen (Session session){
        System.out.println(session.getId());
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新链接加入!当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose (){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一链接关闭!当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage (String message, Session session) throws IOException {
        System.out.println("来自客户端的消息:" + message);
        // 群发消息
        for ( MySocketEndpoint item : webSocketSet){
            item.sendMessage(message,session);
        }
    }

    public void sendMessage (String message,Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public static synchronized  int getOnlineCount (){
        return MySocketEndpoint.onlineCount;
    }

    public static synchronized void addOnlineCount (){
        MySocketEndpoint.onlineCount++;
    }

    public static synchronized void subOnlineCount (){
        MySocketEndpoint.onlineCount--;
    }

}
