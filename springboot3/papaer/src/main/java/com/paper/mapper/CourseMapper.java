package com.paper.mapper;

import com.paper.entity.Course;
import com.paper.entity.Technology;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface CourseMapper {

    void add(Course course);

    void delete(Integer id);

    void update(Course course);

    List<Course> selectByPage(Course course);

    Course selectById(Integer id);


    List<Course> selectCourseByPaperId(Integer paperId);
}
