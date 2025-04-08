package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Admin;
import com.paper.entity.Paper;
import com.paper.entity.Select;
import com.paper.entity.Student;
import com.paper.service.AdminService;
import com.paper.service.PaperService;
import com.paper.service.SelectService;
import com.paper.service.StudentService;
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
    @Resource
    StudentService studentService;
    @Resource
    SelectService selectService;
    @Resource
    PaperService paperService;

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
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  Admin admin) {
        adminService.update(admin);
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
    /**
     * 查询学生数量（后台数量统计）
     */
    @GetMapping("/studentCount")
    public Result studentCount() {
        List<Student> students = studentService.selectAll();
        return Result.success(students.size());
    }
    /**
     * 查询选择数量
     */
    @GetMapping("/selectedPaper")
    public Result selectedPaper() {
        List<Select> selects = selectService.selectAll();
        return Result.success(selects.size());
    }
    /**
     * 查询论文数量
     */
    @GetMapping("/publishedPaper")
    public Result publishedPaper() {
        List<Paper> papers = paperService.selectByFilter(new Paper());
        return Result.success(papers.size());
    }
}
