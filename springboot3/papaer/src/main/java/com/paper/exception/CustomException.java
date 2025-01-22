package com.paper.exception;

import com.paper.common.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-01-22
 */
@Getter
@Setter
public class CustomException extends RuntimeException {
    private String code;
    private String msg;

    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

}
