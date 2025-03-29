package com.paper.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-01-22
 */
@Getter
@Setter
public class Result {
    // 状态码
    private String code;
    // 提示信息
    private String msg;
    // 返回数据
    private Object data;
    public Result() {}
    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Result success(Object data) {
        return new Result("200", "请求成功", data);
    }
    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMsg("请求成功");
        return result;
    }
    public static Result error(String msg) {
        Result result = new Result();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static Result error() {
        Result result = new Result();
        result.setCode("500");
        result.setMsg("请求失败");
        return result;
    }
}
