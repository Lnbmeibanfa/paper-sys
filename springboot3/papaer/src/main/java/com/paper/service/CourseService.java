package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Course;
import com.paper.exception.CustomException;
import com.paper.mapper.CourseMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class CourseService {
    @Resource
    CourseMapper courseMapper;

    public void add(Course course) {
        courseMapper.add(course);
    }

    public PageInfo<Course> selectByPage(Course course, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseMapper.selectByPage(course);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        courseMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(Course course) {
        if (ObjectUtil.isNull(course.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Course dbCourse = courseMapper.selectById(course.getId());
        if (ObjectUtil.isEmpty(dbCourse)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        courseMapper.update(course);
    }

    public Course selectById(Integer id) {
        return courseMapper.selectById(id);
    }

}
