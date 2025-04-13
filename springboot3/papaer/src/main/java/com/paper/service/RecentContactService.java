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
import java.util.Objects;

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
        List<RecentContact> dbRecentContact = recentContactMapper.selectByUserAndContact(recentContact);
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
        List<RecentContact> list = recentContactMapper.selectAllByUserId(recentContact);
        keepRoleConsistent(list);
        addExtraInfo(list);
        return PageInfo.of(list);
    }

    /**
     * 处理数据，使得数据的联系人和用户保持一致。
     * @param recentContacts 从数据库查询的list
     */
    private void keepRoleConsistent(List<RecentContact> recentContacts) {
        Account account = JWTUtil.getCurAccount();
        if (ObjectUtil.isNull(account)) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        for (RecentContact recentContact : recentContacts) {
            if (Objects.equals(account.getRole(), recentContact.getContactRole().name()))  {
                Integer realUserId = recentContact.getContactId();
                Role realUserRole = recentContact.getContactRole();
                recentContact.setContactId(recentContact.getUserId());
                recentContact.setContactRole(recentContact.getUserRole());
                recentContact.setUserId(realUserId);
                recentContact.setUserRole(realUserRole);
            }
        }
    }

    /**
     * 为最近会话添加额外信息
     * @param recentContact 在数据库查到的list
     */
    private void addExtraInfo(List<RecentContact> recentContact) {
        Account account = JWTUtil.getCurAccount();
        if (ObjectUtil.isNull(account)) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        for (RecentContact rc : recentContact) {
            // 如果身份是学生的话，那么查询学生信息，如果是老师那么则查询老师信息
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
                Paper paper = new Paper();
                paper.setId(rc.getPaperId());
                Teacher teacher = teacherService.selectById(teacherId);
                Student student = studentService.selectById(studentId);
                Paper dbPaper = paperService.selectById(paper);
                rc.addTeacherData(teacher);
                rc.addStudentData(student);
                rc.setPaperName(dbPaper.getName());
            }
        }
    }

    public void author(RecentContact recentContact) {
        recentContactMapper.updateSelectable(recentContact);
    }
}
