package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.entity.Teacher;
import com.paper.mapper.TeacherMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Service
public class TeacherService {
    @Resource
    TeacherMapper teacherMapper;

    public void add(Teacher teacher) {
        if (ObjectUtil.isEmpty(teacher.getRole())) {
            teacher.setRole("ADMIN");
        }
        if (ObjectUtil.isEmpty(teacher.getPassword())) {
            teacher.setPassword("123456");
        }
        teacherMapper.add(teacher);
    }

    public PageInfo<Teacher> selectByPage(Teacher teacher, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> list = teacherMapper.selectByPage(teacher);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        teacherMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(Teacher teacher) {
        teacherMapper.update(teacher);
    }
}
