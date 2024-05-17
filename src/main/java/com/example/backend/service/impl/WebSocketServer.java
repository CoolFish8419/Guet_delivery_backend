package com.example.backend.service.impl;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.backend.entity.Chat;
import com.example.backend.mapper.ChatMapper;
import jakarta.annotation.Resource;
import org.json.JSONException;
import org.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
@Component
@Slf4j
@ServerEndpoint("/api/pushMessage/{userId}")
public class WebSocketServer {
    @Resource
    private ChatMapper Chat;// 注入ChatMapper
    /**静态变量，用来记录当前在线连接数*/
    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    /**concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。*/
    private static final ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            // 加入map中
            webSocketMap.put(userId, this);
        } else {
            // 加入map中
            webSocketMap.put(userId, this);
            // 在线数加1
            onlineCount.incrementAndGet();
        }
        log.info("用户连接:" + userId + ",当前在线人数为:" + onlineCount);
        sendMessage("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            // 在线人数减1
            onlineCount.decrementAndGet();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + onlineCount);
    }
    @OnMessage
    public void onMsg(Session session, String message) throws IOException, JSONException {
        JSONObject json = new JSONObject(message);
        Integer toId = json.getInt("toId");
        log.info("发送消息到:" + toId + "，报文:" + message);
        //保存数据到chat表中
        if (StringUtils.isNotBlank(toId.toString()) && webSocketMap.containsKey(toId.toString())) {
            webSocketMap.get(toId.toString()).sendMessage(message);
        } else {
            log.error("用户" + toId + ",不在线！");
        }
    }

    /**
     * 发生异常调用方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     *发送自定义消息
     **/
    public static void sendInfo(String message, String userId) {
        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            log.info("发送消息到:" + userId + "，报文:" + message);
            webSocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }
}
