package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Message;
import com.paper.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 林万奇
 * @since 2025-03-03
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    MessageService messageService;

    /**
     * 分页查询Message
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Message message,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Message> pageInfo = messageService.selectByPage(message, pageSize, pageNum);
        return Result.success(pageInfo);
    }

}
