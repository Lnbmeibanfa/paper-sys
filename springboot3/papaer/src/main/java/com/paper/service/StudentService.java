package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Account;
import com.paper.entity.Student;
import com.paper.exception.CustomException;
import com.paper.mapper.StudentMapper;
import com.paper.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-31
 */
@Service
public class StudentService {
    @Resource
    StudentMapper studentMapper;

    public void add(Student student) {
        if (ObjectUtil.isEmpty(student.getRole())) {
            student.setRole("STUDENT");
        }
        if (ObjectUtil.isEmpty(student.getPassword())) {
            student.setPassword("123456");
        }
        studentMapper.add(student);
    }

    public PageInfo<Student> selectByPage(Student student, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> list = studentMapper.selectByPage(student);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        studentMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(Student student) {
        if (ObjectUtil.isNull(student.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Student dbStudent = studentMapper.selectById(student.getId());
        if (ObjectUtil.isEmpty(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        studentMapper.update(student);
    }


    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }

    public Account login(Account account) {
        Student dbStudent = studentMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isEmpty(dbStudent)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        } else if(!dbStudent.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        // token
        String token = JWTUtil.createJWT(dbStudent.getId() + "-" + dbStudent.getRole(), dbStudent.getPassword());
        dbStudent.setToken(token);
        return dbStudent;
    }
}
