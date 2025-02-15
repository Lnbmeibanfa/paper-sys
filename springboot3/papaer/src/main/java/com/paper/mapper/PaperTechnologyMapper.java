package com.paper.mapper;

import com.paper.entity.PaperTechnology;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface PaperTechnologyMapper {

    void add(PaperTechnology paperTechnology);

    List<PaperTechnology> selectByPage(PaperTechnology paperTechnology);

    void delete(Integer id);

    void deleteByPaperId(Integer paperId);

    void update(PaperTechnology paperTechnology);

    PaperTechnology selectById(Integer id);


}
