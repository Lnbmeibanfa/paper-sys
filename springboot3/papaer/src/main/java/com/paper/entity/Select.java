package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-03-09
 */
@Getter
@Setter
public class Select {
    private Integer id;
    private Integer studentId;
    private Integer paperId;
    public Select() {}
    public Select(Integer StudentId) {
        this.studentId = StudentId;
    }
}
