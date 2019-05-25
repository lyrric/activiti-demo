package com.github.lyrric.controller;

import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.LeaderTaskListVO;
import com.github.lyrric.model.vo.TeacherTaskListVO;
import com.github.lyrric.service.LeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
@RequestMapping(value = "/api/v1.0/leader")
@Api(description = "教务处")
public class LeaderController {

    @Resource
    private LeaderService leaderService;

    @ApiOperation(value = "我的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "int", paramType = "query", value = "页号", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", dataType = "int", paramType = "query", value = "页大小", required = true, defaultValue = "10")
    })
    @GetMapping(value = "/list")
    public PageResult<LeaderTaskListVO> list(Integer pageNum, Integer pageSize){
        return leaderService.list(pageNum, pageSize);
    }

    @ApiOperation(value = "/审批流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "int", paramType = "query", value = "id", required = true),
            @ApiImplicitParam(name = "pass", dataType = "bool", paramType = "query", value = "是否通过", required = true, defaultValue = "true")
    })
    @GetMapping(value = "/approval")
    public void approval(Integer id, boolean pass) throws BusinessException {
        leaderService.approval(id, pass);
    }


}
