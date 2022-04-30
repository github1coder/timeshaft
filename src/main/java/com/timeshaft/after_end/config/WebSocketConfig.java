package com.timeshaft.after_end.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * feishu
 * Websocket相关配置
 *
 * @author : dxt
 * @since : 2022-04-17 13:53
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    /**
     * 设置stomp端点, 即允许客户端通过此端点(/websocket)进行连接, withSockJS允许不支持websocket的浏览器使用此服务
     *
     * @param stompEndpointRegistry stomp端点注册器
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置client路径, 其中/user与/group是client订阅的路径前缀, /app是client发送至server所使用的路径前缀
     *
     * @param messageBrokerRegistry 消息路径配置器
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.enableSimpleBroker("/user", "/group");
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
    }


}
