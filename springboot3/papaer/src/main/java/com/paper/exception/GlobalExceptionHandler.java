package com.paper.exception;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.paper.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 林万奇
 * @since 2025-01-23
 */
@RestControllerAdvice("com.paper.controller")
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(CustomException e) {
        log.error("异常信息：" + e);
        return Result.error(e.getCode(), e.getMsg());
    }

}
