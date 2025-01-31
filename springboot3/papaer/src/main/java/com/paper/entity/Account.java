package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Getter
@Setter
public class Account {
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private String name;
    private String role;
    private String token;
}
