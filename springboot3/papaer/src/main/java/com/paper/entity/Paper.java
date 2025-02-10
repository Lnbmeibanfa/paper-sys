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
    private Integer courseId;
    private String courseName;
    private Integer languageId;
    private String languageName;
    private Integer technologyId;
    private String technologyName;
    private String content;
    private String name;
    private String requirement;
    private List<String> tagsList;
    private String tags;
    private String studentGroup;
    private String type;

}
