package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Student;
import com.paper.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-31
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    StudentService studentService;

    /**
     * 添加student
     */
    @PostMapping("/add")
    public Result add (@RequestBody Student student) {
        studentService.add(student);
        return Result.success();
    }

    /**
     * 单个删除student
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        studentService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除student
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        studentService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  Student student) {
        studentService.update(student);
        return Result.success();
    }

    /**
     * 分页查询student
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Student student,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Student> pageInfo =studentService.selectByPage(student, pageSize, pageNum);
        return Result.success(pageInfo);
    }


}
