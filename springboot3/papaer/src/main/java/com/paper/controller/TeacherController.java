package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Paper;
import com.paper.entity.Teacher;
import com.paper.service.PaperService;
import com.paper.service.TeacherService;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    TeacherService teacherService;
    @Resource
    PaperService paperService;
    /**
     * 添加teacher
     */
    @PostMapping("/add")
    public Result add (@RequestBody Teacher teacher) {
        teacherService.add(teacher);
        return Result.success();
    }

    /**
     * 单个删除teacher
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        teacherService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除teacher
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        teacherService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  Teacher teacher) {
        teacherService.update(teacher);
        return Result.success();
    }

    /**
     * 分页查询teacher
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Teacher teacher,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Teacher> pageInfo =teacherService.selectByPage(teacher, pageSize, pageNum);
        return Result.success(pageInfo);
    }
    /**
     * 根据id查询teacher
     */
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        Teacher teacher = teacherService.selectById(id);
        return Result.success(teacher);
    }
    /**
     * 根据id查询该id下所属论文数量
     */
    @GetMapping("/selectPaperById")
    public Result selectPaperById(Integer id) {
        List<Paper> papers = paperService.selectByFilter(new Paper(null, id, null));
        return Result.success(papers.size());
    }
}
