package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Admin;
import com.paper.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    /**
     * 添加admin
     */
    @PostMapping("/add")
    public Result add (@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    /**
     * 单个删除admin
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除admin
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        adminService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 分页查询admin
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Admin admin,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Admin> pageInfo =adminService.selectByPage(admin, pageSize, pageNum);
        return Result.success(pageInfo);
    }
}
