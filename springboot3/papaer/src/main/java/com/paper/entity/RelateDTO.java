package com.paper.entity;

/**
 * @author 林万奇
 * @since 2025-03-25
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelateDTO {
    private Integer studentId;
    private Integer paperId;
    // 质数指数
    private Integer index;

    public RelateDTO(Integer studentId, Integer paperId, Integer index) {
        this.studentId = studentId;
        this.paperId = paperId;
        this.index = index;
    }
}
