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
    private Integer StudentId;
    private Integer PaperId;

    public Collect(Integer id) {
        this.StudentId = id;
    }
}
