package com.paper.mapper;

import com.paper.entity.PaperCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface PaperCourseMapper {

    void add(PaperCourse paperCourse);

    List<PaperCourse> selectByPage(PaperCourse paperCourse);

    void delete(Integer id);

    void update(PaperCourse paperCourse);

    PaperCourse selectById(Integer id);


}
