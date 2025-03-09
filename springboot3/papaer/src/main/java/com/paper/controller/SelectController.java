package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Select;
import com.paper.service.SelectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author 林万奇
 * @since 2025-02-22
 */
@RestController
@RequestMapping("/select")
public class SelectController {
    @Resource
    SelectService selectService;

    /**
     * 添加select
     */
    @PostMapping("/add")
    public Result add (@RequestBody Select select) {
        selectService.add(select);
        return Result.success();
    }

    /**
     * 单个删除select
     */
    @DeleteMapping("/deleteBySelect")
    public Result deleteById(@RequestParam Integer studentId, @RequestParam Integer paperId) {
        selectService.deleteBySelect(studentId, paperId);
        return Result.success();
    }



    /**
     * 分页查询select
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Select select,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Select> pageInfo =selectService.selectByPage(select, pageSize, pageNum);
        return Result.success(pageInfo);
    }

    /**
     * 根据studentId 和 paperId 查询
     */
    @GetMapping("/selectBySelect")
    public Result selectBySelect (Select select) {
        return Result.success(selectService.selectBySelect(select));
    }
}
