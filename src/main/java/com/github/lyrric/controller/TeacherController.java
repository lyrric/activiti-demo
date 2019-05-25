package com.github.lyrric.controller;

import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.StudentTaskListVO;
import com.github.lyrric.model.vo.TeacherTaskListVO;
import com.github.lyrric.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.SunHints;

import javax.annotation.Resource;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
@RequestMapping(value = "/api/v1.0/teacher")
@Api(description = "班主任")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @ApiOperation(value = "我的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "int", paramType = "query", value = "页号", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", dataType = "int", paramType = "query", value = "页大小", required = true, defaultValue = "10")
    })
    @GetMapping(value = "/list")
    public PageResult<TeacherTaskListVO> list(Integer pageNum, Integer pageSize){
        return teacherService.list(pageNum, pageSize);
    }

    @ApiOperation(value = "/审批流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query", value = "id", required = true),
    })
    @GetMapping(value = "/approval")
    public void approval(Integer id) throws BusinessException {
        teacherService.approval(id);
    }

}
