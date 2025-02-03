package com.paper.controller;

import com.paper.common.Result;
import com.paper.common.enums.Role;
import com.paper.entity.Account;
import com.paper.service.AdminService;
import com.paper.service.StudentService;
import com.paper.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 林万奇
 * @since 2025-02-01
 */
@RestController
@Slf4j
public class WebController {
    @Resource
    StudentService studentService;
    @Resource
    TeacherService teacherService;

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account loginAccount = null;
        if (account.getRole().equals(Role.TEACHER.name())) {
            loginAccount = teacherService.login(account);
        } else if (account.getRole().equals(Role.STUDENT.name())) {
            loginAccount = studentService.login(account);
        }
        return Result.success(loginAccount);
    }
}
