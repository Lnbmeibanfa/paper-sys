package com.paper.controller;

import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.entity.RecentContact;
import com.paper.service.RecentContactService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author 林万奇
 * @since 2025-03-03
 */
@RestController
@RequestMapping("/recentContact")
public class RecentContactController {
    @Resource
    RecentContactService recentContactService;

    /**
     * 添加新的rc
     */
    @PutMapping("/add")
    public Result add(@RequestBody RecentContact recentContact) {
        recentContactService.add(recentContact);
        return Result.success();
    }

    /**
     * 分页查询RecentContact
     */
    @GetMapping("/selectByPage")
    public Result selectByPage (RecentContact recentContact,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<RecentContact> pageInfo = recentContactService.selectAllByPage(recentContact, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
