package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-02-11
 */
@Getter
@Setter
public class PaperTechnology {
    private Integer id;
    private Integer paperId;
    private Integer technologyId;
    public PaperTechnology(Integer paperId, Integer technologyId) {
        this.paperId = paperId;
        this.technologyId = technologyId;
    }
}
