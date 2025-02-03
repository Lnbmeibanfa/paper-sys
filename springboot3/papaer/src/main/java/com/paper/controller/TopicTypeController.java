package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.TopicType;
import com.paper.service.TopicTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@RestController
@RequestMapping("/topicType")
public class TopicTypeController {
    @Resource
    TopicTypeService topicTypeService;

    /**
     * 添加topicType
     */
    @PostMapping("/add")
    public Result add (@RequestBody TopicType topicType) {
        topicTypeService.add(topicType);
        return Result.success();
    }

    /**
     * 单个删除topicType
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        topicTypeService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除topicType
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        topicTypeService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  TopicType topicType) {
        topicTypeService.update(topicType);
        return Result.success();
    }

    /**
     * 分页查询topicType
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (TopicType topicType,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<TopicType> pageInfo =topicTypeService.selectByPage(topicType, pageSize, pageNum);
        return Result.success(pageInfo);
    }
}
