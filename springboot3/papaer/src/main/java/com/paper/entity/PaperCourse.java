package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-02-11
 */
@Getter
@Setter
public class PaperCourse {
    private Integer id;
    private Integer paperId;
    private Integer courseId;

    public PaperCourse(Integer paperId, Integer courseId) {
        this.paperId = paperId;
        this.courseId = courseId;
    }
}
