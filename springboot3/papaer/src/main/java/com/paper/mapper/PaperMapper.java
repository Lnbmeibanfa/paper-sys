package com.paper.mapper;

import com.paper.entity.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface PaperMapper {

    void add(Paper paper);

    List<Paper> selectByPage(Paper paper);

    void delete(Integer id);

    void update(Paper paper);

    Paper selectById(Integer id);

    List<Paper> selectByFilter(Paper paper);
}
