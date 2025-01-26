package com.paper.controller;

import com.paper.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 林万奇
 * @since 2025-01-23
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Result hello() {
        return Result.success("hello");
    }
}
