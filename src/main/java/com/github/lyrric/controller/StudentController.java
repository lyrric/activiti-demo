package com.github.lyrric.controller;

import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.StudentTaskListVO;
import com.github.lyrric.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.print.attribute.standard.MediaSize;
import java.util.Date;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
@RequestMapping(value = "/api/v1.0/student")
@Api(description = "学生端")
public class StudentController {

    @Resource
    private StudentService studentService;

    @ApiOperation(value = "提交申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", dataType = "Date", paramType = "query", value = "请假开始时间", required = true, defaultValue = "2019-05-24"),
            @ApiImplicitParam(name = "day", dataType = "int", paramType = "query", value = "请假天数", required = true, defaultValue = "1")
    })
    @GetMapping(value = "/create")
    public void create(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, Integer day){
        studentService.create(startTime, day);
    }

    @ApiOperation(value = "我的申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "int", paramType = "query", value = "页号", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", dataType = "int", paramType = "query", value = "页大小", required = true, defaultValue = "10")
    })
    @GetMapping(value = "/list")
    public PageResult<StudentTaskListVO> list(Integer pageNum, Integer pageSize){
        return studentService.list(pageNum, pageSize);
    }

    @ApiOperation(value = "修改并重新提交申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query", value = "id", required = true),
            @ApiImplicitParam(name = "startTime", dataType = "Date", paramType = "query", value = "请假开始时间", required = true, defaultValue = "2019-05-24"),
            @ApiImplicitParam(name = "day", dataType = "int", paramType = "query", value = "请假天数", required = true, defaultValue = "1")
    })
    @GetMapping(value = "/updateAndCommit")
    public void updateAndCommit(Integer id, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, int day) throws BusinessException {
        studentService.updateAndCommit(id, startTime, day);
    }


}
