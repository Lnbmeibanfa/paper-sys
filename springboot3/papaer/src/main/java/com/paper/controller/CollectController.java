package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Collect;
import com.paper.service.CollectService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-02-22
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Resource
    CollectService collectService;

    /**
     * 添加collect
     */
    @PostMapping("/add")
    public Result add (@RequestBody Collect collect) {
        collectService.add(collect);
        return Result.success();
    }

    /**
     * 单个删除collect
     */
    @DeleteMapping("/deleteByCollect")
    public Result deleteById(@RequestParam Integer studentId, @RequestParam Integer paperId) {
        collectService.deleteByCollect(studentId, paperId);
        return Result.success();
    }



    /**
     * 分页查询collect
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Collect collect,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Collect> pageInfo =collectService.selectByPage(collect, pageSize, pageNum);
        return Result.success(pageInfo);
    }

    /**
     * 根据studentId 和 paperId 查询
     */
    @GetMapping("/selectByCollect")
    public Result selectByCollect (Collect collect) {
        return Result.success(collectService.selectByCollect(collect));
    }
}
