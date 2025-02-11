package com.paper.mapper;

import com.paper.entity.PaperLanguage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface PaperLanguageMapper {

    void add(PaperLanguage paperLanguage);

    List<PaperLanguage> selectByPage(PaperLanguage paperLanguage);

    void delete(Integer id);

    void update(PaperLanguage paperLanguage);

    PaperLanguage selectById(Integer id);


}
