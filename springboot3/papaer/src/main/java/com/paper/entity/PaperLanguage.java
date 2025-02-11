package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-02-11
 */
@Getter
@Setter
public class PaperLanguage {
    private Integer id;
    private Integer paperId;
    private Integer languageId;
    public PaperLanguage(Integer paperId, Integer languageId) {
        this.paperId = paperId;
        this.languageId = languageId;
    }
}
