package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Account;
import com.paper.entity.Teacher;
import com.paper.entity.Teacher;
import com.paper.exception.CustomException;
import com.paper.mapper.TeacherMapper;
import com.paper.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (ObjectUtil.isNull(teacher.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Teacher dbTeacher = teacherMapper.selectById(teacher.getId());
        if (ObjectUtil.isEmpty(dbTeacher)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        teacherMapper.update(teacher);
    }

    public Teacher selectById(Integer id) {
        return teacherMapper.selectById(id);
    }

    public Account login(Account account) {
        Teacher dbTeacher = teacherMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isEmpty(dbTeacher)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        } else if (!dbTeacher.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        // token
        String token = JWTUtil.createJWT(dbTeacher.getId() + "-" + dbTeacher.getRole(), dbTeacher.getPassword());
        account.setToken(token);
        return account;
    }
}
