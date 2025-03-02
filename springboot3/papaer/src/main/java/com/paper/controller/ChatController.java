package com.paper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 林万奇
 * @since 2025-02-27
 */
@RestController
public class ChatController {
    @Autowired
    SimpMessagingTemplate template;
}
