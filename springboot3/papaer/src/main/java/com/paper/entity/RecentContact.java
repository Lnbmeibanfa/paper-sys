package com.paper.entity;

import com.paper.common.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 林万奇
 * @since 2025-02-27
 */
@Getter
@Setter
public class RecentContact {
    private Integer userId;
    private Role userRole;
    private Integer contactId;
    private Role contactRole;
    private String lastMessage;
    private Integer paperId;
    private LocalDateTime lastActive;
    private String studentName;
    private String studentAvatar;
    private String teacherName;
    private String teacherAvatar;
    private String teacherResearchDirection;
    private String paperName;
    private Boolean selectable;

    public static RecentContact messageToRecentContact(Message message) {
        RecentContact recentContact = new RecentContact();
        recentContact.setUserId(message.getSendId());
        recentContact.setUserRole(message.getSendRole());
        recentContact.setContactId(message.getReceiverId());
        recentContact.setContactRole(message.getReceiverRole());
        recentContact.setLastActive(LocalDateTime.now());
        recentContact.setLastMessage(message.getContent());
        recentContact.setPaperId(message.getPaperId());
        return recentContact;
    }

    public RecentContact() {}

    public void addStudentData (Student student) {
        this.studentName = student.getName();
        this.studentAvatar = student.getAvatar();
    }
    public void addTeacherData(Teacher teacher) {
        this.teacherName = teacher.getName();
        this.teacherAvatar = teacher.getAvatar();
        this.teacherResearchDirection = teacher.getResearchDirection();
    }
}
