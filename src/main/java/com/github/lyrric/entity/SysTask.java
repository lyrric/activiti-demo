package com.github.lyrric.entity;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_task")
@Data
public class SysTask extends BaseEntity{

    /**
     * 学生ID
     */
    @Column(name = "student_id")
    private Integer studentId;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 请假开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 请假天数
     */
    private Integer day;

    /**
     * 提交时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 进程实例ID
     */
    @Column(name = "pro_inst_id")
    private String proInstId;



}