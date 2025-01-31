package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Getter
@Setter
public class Teacher extends Account {
    private String address;
    private String phone;
    private String wechat;
    private String QQ;
    private String email;
    private String researchDirection;
    private String status;
}
