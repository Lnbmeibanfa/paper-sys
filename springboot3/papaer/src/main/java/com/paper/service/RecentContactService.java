package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.common.enums.Role;
import com.paper.entity.*;
import com.paper.exception.CustomException;
import com.paper.mapper.RecentContactMapper;
import com.paper.util.JWTUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Slf4j
@Service
public class RecentContactService {
    @Resource
    RecentContactMapper recentContactMapper;

    @Resource
    StudentService studentService;

    @Resource
    TeacherService teacherService;

    @Resource
    PaperService paperService;
    @Autowired
    private JWTUtil jWTUtil;

    public void updateOrAdd(RecentContact recentContact) {
        Integer userId = recentContact.getUserId();
        if (ObjectUtil.isEmpty(userId)) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        List<RecentContact> dbRecentContact = recentContactMapper.selectByUserId(recentContact);
        if (ObjectUtil.isEmpty(dbRecentContact)) {
            recentContactMapper.add(recentContact);
        } else {
            if (dbRecentContact.size() > 1) {
                throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
            }
            recentContactMapper.update(recentContact);
        }

    }

    public void add(RecentContact recentContact) {
        // 检查是否已经插入了，没插入则插入，插入了则报错
    }

    public PageInfo<RecentContact> selectAllByPage(RecentContact recentContact, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RecentContact> list = recentContactMapper.selectByUserId(recentContact);
        Account account = JWTUtil.getCurAccount();
        if (ObjectUtil.isNull(account)) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        for (RecentContact rc : list) {
            if (account.getRole().equals(Role.STUDENT.name())) {
                Integer studentId = rc.getUserId();
                Integer teacherId = rc.getContactId();
                Paper paper = new Paper();
                paper.setId(rc.getPaperId());
                Student student = studentService.selectById(studentId);
                Teacher teacher = teacherService.selectById(teacherId);
                Paper dbPaper = paperService.selectById(paper);
                rc.addStudentData(student);
                rc.addTeacherData(teacher);
                rc.setPaperName(dbPaper.getName());
            } else if (account.getRole().equals(Role.TEACHER.name())) {
                Integer teacherId = rc.getUserId();
                Integer studentId = rc.getContactId();
                Teacher teacher = teacherService.selectById(teacherId);
                rc.addTeacherData(teacher);
                Student student = studentService.selectById(studentId);
                rc.addStudentData(student);
            }

        }
        return PageInfo.of(list);
    }

    public void author(RecentContact recentContact) {
        recentContactMapper.updateSelectable(recentContact);
    }
}
