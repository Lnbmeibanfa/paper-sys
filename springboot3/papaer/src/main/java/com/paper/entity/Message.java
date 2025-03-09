package com.paper.entity;

import com.paper.common.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 林万奇
 * @since 2025-02-27
 */
@Getter
@Setter
public class Message {
    private Integer id;
    private Integer sendId;
    private Role sendRole;
    private Integer receiverId;
    private Role receiverRole;
    private Integer paperId;
    private String content;
    private LocalDateTime createTime;
}
