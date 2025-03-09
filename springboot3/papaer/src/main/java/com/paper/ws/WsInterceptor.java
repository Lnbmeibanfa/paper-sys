package com.paper.ws;

import cn.hutool.db.Session;
import com.paper.entity.Account;
import com.paper.service.MessageService;
import com.paper.util.JWTUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 林万奇
 * @since 2025-03-03
 */
@Component
@Slf4j
public class WsInterceptor extends HttpSessionHandshakeInterceptor {


    Map<Account, Session> sessionMap = new ConcurrentHashMap<>();
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info(request.getURI().getQuery());
        String token = request.getURI().getQuery().split("=")[1];
        Account account = JWTUtil.getAccountByToken(token);
        attributes.put("account", account);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        log.info(request.getRemoteAddress().toString() + "完成握手");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
