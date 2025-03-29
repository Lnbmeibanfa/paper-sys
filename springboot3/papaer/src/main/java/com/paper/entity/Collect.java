package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-02-22
 */
@Getter
@Setter
public class Collect {
    private Integer id;
    private Integer studentId;
    private Integer paperId;
    public  Collect(){}

    public Collect(Integer id) {
        this.studentId = id;
    }
}
