package com.paper.util;


import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.paper.common.enums.Constants;
import com.paper.common.enums.Role;
import com.paper.entity.Account;
import com.paper.service.AdminService;
import com.paper.service.TeacherService;
import com.paper.service.StudentService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * @author 林万奇
 * @since 2024-12-8
 */
@Slf4j
@Component
public class JWTUtil {
    @Resource
    private AdminService adminService;
    @Resource
    private TeacherService employService;
    @Resource
    private StudentService userService;

    private static AdminService staticAdminService;
    private static TeacherService staticTeacherService;
    private static StudentService staticStudentService;

    @PostConstruct
    private void init() {
        staticAdminService = adminService;
        staticTeacherService = employService;
        staticStudentService = userService;
    }
    /**
     * 根据 1-ADMIN 和 密码创建token
     */
    public static String createJWT(String data, String sign) {
        // token的创建也包含了,token需要哪些要素
        // 需要Audience,也就是这个token的受众,或者说是用户的信息, 1-ADMIN
        // token有效时间,为一天
        // token的加密算法
        return JWT.create().withAudience(data)
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 获取当前登录用户
     */
    public static Account getCurAccount() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.token);
            return getAccountByToken(token);
        } catch (Exception e) {
            log.error("获取当前登录用户出错", e);
        }
        return null;
    }

    /**
     * 根据token获取用户信息
     */
    public static Account getAccountByToken(String token) {
        String audience = JWT.decode(token).getAudience().getFirst();
        String[] userRole = audience.split("-");
        String userId = userRole[0];
        String role = userRole[1];
        if (Role.ADMIN.name().equals(role)) {
            return staticAdminService.selectById(Integer.valueOf(userId));
        } else if (Role.TEACHER.name().equals(role)) {
            return staticTeacherService.selectById(Integer.valueOf(userId));
        } else if (Role.STUDENT.name().equals(role)) {
            return staticStudentService.selectById(Integer.valueOf(userId));
        }
        return null;
    }
}
