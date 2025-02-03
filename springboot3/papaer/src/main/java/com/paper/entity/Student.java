package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-01-31
 */
@Getter
@Setter
public class Student extends Account {
    private Integer id;
    private String username;
    private String name;
    private String password;
    private String avatar;
    private String email;
    private String phone;
    private String wechat;
    private String QQ;
    private String role;
}
