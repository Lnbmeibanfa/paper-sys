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
    private Integer studentId;
    private String studentName;
    private List<Integer> courseIds;
    private List<Integer> languageIds;
    private List<Integer> technologyIds;
    private List<String> courseNames;
    private List<String> languageNames;
    private List<String> technologyNames;
    private String content;
    private String name;
    private String resource;
    private String requirement;
    private String studentGroup;
    private String type;
    private Double gpa;

}
