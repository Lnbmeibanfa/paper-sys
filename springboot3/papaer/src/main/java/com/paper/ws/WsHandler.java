package com.paper.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paper.common.enums.Role;
import com.paper.entity.Account;
import com.paper.entity.Message;
import com.paper.entity.RecentContact;
import com.paper.service.MessageService;
import com.paper.service.RecentContactService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * @author 林万奇
 * @since 2025-03-03
 */
@Slf4j
@Component
public class WsHandler extends AbstractWebSocketHandler {

    @Resource
    MessageService messageService;
    @Resource
    RecentContactService recentContactService;
    private static final Map<Account, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Account account = (Account) session.getAttributes().get("account");
        if (account != null) {
            sessionMap.put(account, session);
        }
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(message.getPayload(), Message.class);
        messageService.add(msg);
        recentContactService.updateOrAdd(RecentContact.messageToRecentContact(msg));
        Integer receiverId = msg.getReceiverId();
        Role receiverRole = msg.getReceiverRole();
        WebSocketSession targerSession = getSessionByIdAndRole(receiverId, receiverRole);
        if (targerSession != null) {
            try {
                log.info(Level.INFO + "尝试向会话 ID: {0} 发送消息", targerSession.getId());targerSession.sendMessage(new TextMessage("123456"));
                log.info(Level.INFO + "消息发送成功，会话 ID: {0}", targerSession.getId());
            } catch (Exception e) {
                log.info(Level.SEVERE +"发送消息时出现异常，会话 ID: " + targerSession.getId(), e);
            }
        }
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    public static WebSocketSession getSessionByIdAndRole(int targetId, Role targetRole) {
        // 遍历哈希表的所有键值对
        for (Map.Entry<Account, WebSocketSession> entry : sessionMap.entrySet()) {
            Account account = entry.getKey();
            // 比较键中的 id 和 role 字段
            if (account.getId() == targetId && account.getRole().equals(targetRole.name())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
