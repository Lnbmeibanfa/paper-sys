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
public class RecentContact {
    private Integer userId;
    private Role userRole;
    private Integer contactId;
    private Role contactRole;
    private String lastMessage;
    private LocalDateTime lastActive;
}
