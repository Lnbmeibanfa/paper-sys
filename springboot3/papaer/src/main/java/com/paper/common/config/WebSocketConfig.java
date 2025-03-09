package com.paper.common.config;

import com.paper.ws.WsHandler;
import com.paper.ws.WsInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 林万奇
 * @since 2025-02-25
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    WsHandler wsHandler;
    @Resource
    WsInterceptor wsInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, "/chat").addInterceptors(wsInterceptor).setAllowedOrigins("*");
    }
}
