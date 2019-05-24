package com.github.lyrric.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
@Data
public class StudentTaskListVO {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 学生Id
     */
    @ApiModelProperty("学生Id")
    private Integer studentId;
    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;
    /**
     * 请假开始时间
     */
    @ApiModelProperty("请假开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 请假天数
     */
    @ApiModelProperty("请假天数")
    private Integer day;
    /**
     * 提交时间
     */
    @ApiModelProperty("提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 流程状态
     */
    @ApiModelProperty("流程状态")
    private String status;

    @ApiModelProperty("节点列表")
    private List<TaskIns> taskInsList;

    @Data
    public static class TaskIns{

        @ApiModelProperty("节点ID")
        private String id;

        @ApiModelProperty("节点名称")
        private String taskName;

        @ApiModelProperty("节点开始时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startTime;

        @ApiModelProperty("节点结束时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;

        @ApiModelProperty("节点是否通过")
        private String status;
    }

}
