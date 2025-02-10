package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.Language;
import com.paper.service.LanguageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@RestController
@RequestMapping("/language")
public class LanguageController {
    @Resource
    LanguageService languageService;

    /**
     * 添加language
     */
    @PostMapping("/add")
    public Result add (@RequestBody Language language) {
        languageService.add(language);
        return Result.success();
    }

    /**
     * 单个删除language
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        languageService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除language
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch (@RequestBody List<Integer> ids) {
        languageService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改update
     */
    @PutMapping("/update")
    public Result update (@RequestBody  Language language) {
        languageService.update(language);
        return Result.success();
    }

    /**
     * 分页查询language
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (Language language,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Language> pageInfo =languageService.selectByPage(language, pageSize, pageNum);
        return Result.success(pageInfo);
    }
}
