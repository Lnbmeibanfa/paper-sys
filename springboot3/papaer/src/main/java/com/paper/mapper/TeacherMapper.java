package com.paper.mapper;

import com.paper.entity.Teacher;
import com.paper.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface TeacherMapper {

    void add(Teacher teacher);

    List<Teacher> selectByPage(Teacher teacher);

    void delete(Integer id);

    void update(Teacher teacher);

    Teacher selectById(Integer id);

    Teacher selectByUsername(String username);
}
