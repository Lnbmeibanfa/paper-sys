package com.paper.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

/**
 * @author 林万奇
 * @since 2025-02-08
 */
@Getter
@Setter
public class Paper {
    private Integer id;
    private Integer teacherId;
    private String teacherName;
    private String teacherAddress;
    private String teacherPhone;
    private String teacherWechat;
    private String teacherEmail;
    private String teacherQQ;
    private String teacherResearchDirection;
    private String teacherAvatar;
    private Integer studentId;
    private String studentName;
    private List<Integer> courseIds;
    private List<Integer> languageIds;
    private List<Integer> technologyIds;
    private List<Course> courses;
    private List<Language> languages;
    private List<Technology> technologies;
    private String content;
    private String name;
    private String resource;
    private String requirement;
    private String studentGroup;
    private String type;
    private Double gpa;
    // 根据论文名或者教师名查询字段
    private String keyword;
    // 是否添加收藏筛选条件
    private Boolean onlyShowCollect;
    // 该论文是否要被选择
    private Boolean isSelect;
    public  Paper() {
    }
    public Paper(Integer id, Integer teacherId, Integer studentId) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
    }
}
