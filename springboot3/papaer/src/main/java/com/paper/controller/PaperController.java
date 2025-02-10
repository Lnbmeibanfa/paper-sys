package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Paper;
import com.paper.service.PaperService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    PaperService paperService;

    /**
     * 添加paper
     */
    @PostMapping("/add")
    public Result add (@RequestBody Paper paper) {
        paperService.add(paper);
        return Result.success();
    }

    /**
     * 单个删除paper
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        paperService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除paper
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        paperService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  Paper paper) {
        paperService.update(paper);
        return Result.success();
    }

    /**
     * 分页查询paper
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Paper paper,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Paper> pageInfo =paperService.selectByPage(paper, pageSize, pageNum);
        return Result.success(pageInfo);
    }
}
