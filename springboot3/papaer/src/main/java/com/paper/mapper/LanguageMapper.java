package com.paper.mapper;

import com.paper.entity.Language;
import com.paper.entity.Technology;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface LanguageMapper {

    void add(Language language);

    void delete(Integer id);

    void update(Language language);

    List<Language> selectByPage(Language language);

    Language selectById(Integer id);

    List<Language> selectLanguageByPaperId(Integer paperId);

}
