package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Course;
import com.paper.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    CourseService courseService;

    /**
     * 添加course
     */
    @PostMapping("/add")
    public Result add (@RequestBody Course course) {
        courseService.add(course);
        return Result.success();
    }

    /**
     * 单个删除course
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        courseService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除course
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        courseService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  Course course) {
        courseService.update(course);
        return Result.success();
    }

    /**
     * 分页查询course
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Course course,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Course> pageInfo =courseService.selectByPage(course, pageSize, pageNum);
        return Result.success(pageInfo);
    }
}
