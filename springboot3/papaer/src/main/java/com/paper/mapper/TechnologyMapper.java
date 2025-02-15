package com.paper.mapper;

import com.paper.entity.Technology;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface TechnologyMapper {

    void add(Technology technology);

    void delete(Integer id);

    void update(Technology technology);

    List<Technology> selectByPage(Technology technology);

    Technology selectById(Integer id);

    List<Technology> selectTechnologyByPaperId(Integer paperId);

}
