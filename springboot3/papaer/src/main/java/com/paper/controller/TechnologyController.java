package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Technology;
import com.paper.service.TechnologyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@RestController
@RequestMapping("/technology")
public class TechnologyController {
    @Resource
    TechnologyService technologyService;

    /**
     * 添加technology
     */
    @PostMapping("/add")
    public Result add (@RequestBody Technology technology) {
        technologyService.add(technology);
        return Result.success();
    }

    /**
     * 单个删除technology
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        technologyService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除technology
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        technologyService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  Technology technology) {
        technologyService.update(technology);
        return Result.success();
    }

    /**
     * 分页查询technology
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Technology technology,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Technology> pageInfo =technologyService.selectByPage(technology, pageSize, pageNum);
        return Result.success(pageInfo);
    }
}
