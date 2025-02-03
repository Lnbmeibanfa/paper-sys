package com.paper.mapper;

import com.paper.entity.Student;
import com.paper.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-31
 */
@Mapper
public interface StudentMapper {

    void add(Student student);

    List<Student> selectByPage(Student student);

    void delete(Integer id);

    void update(Student student);

    Student selectById(Integer id);

    Student selectByUsername(String username);
}
