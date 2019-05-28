package com.github.lyrric.util;

import com.github.lyrric.command.JumpToTargetNodeCmd;
import com.github.lyrric.entity.SysTask;
import com.github.lyrric.mapper.SysTaskMapper;
import com.github.lyrric.mapper.TeacherMapper;
import com.github.lyrric.model.BusinessException;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019-05-28.
 *
 * @author wangxiaodong
 */
@Component
public class ActivitiUtil {

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private SysTaskMapper sysTaskMapper;
    @Resource
    private HistoryService historyService;
    @Resource
    private TaskService taskService;
    @Resource
    private ManagementService managementService;

    /**
     * 发起人节点
     */
    private final String FIRST_NODE = "leave_request";

    /**
     * 退回流程到发起人
     * @param sysTaskId
     * @param userType 操作人类型，用户判断是否有权限操作
     */
    public void rollbackToFirstTask(int sysTaskId, String userType) throws BusinessException {
        check(sysTaskId, userType);
        SysTask sysTask = sysTaskMapper.selectByPrimaryKey(sysTaskId);
        Task task = taskService.createTaskQuery()
                .processInstanceId(sysTask.getProInstId())
                .singleResult();
        managementService.executeCommand(new JumpToTargetNodeCmd(task.getId(), FIRST_NODE));
    }

    /**
     * 完成任务
     * @param sysTaskId
     * @param var
     * @param userType
     * @throws BusinessException
     */
    public void completeTask(int sysTaskId, Map<String, Object> var, String userType) throws BusinessException {
        check(sysTaskId, userType);
        SysTask sysTask = sysTaskMapper.selectByPrimaryKey(sysTaskId);
        Task task = taskService.createTaskQuery()
                .processInstanceId(sysTask.getProInstId())
                .singleResult();
        taskService.complete(task.getId(), var);
    }

    /**
     * 校验用户是否有权限操作
     * @param sysTaskId
     * @param userType
     */
    private void check(int sysTaskId, String userType) throws BusinessException {
        SysTask sysTask = sysTaskMapper.selectByPrimaryKey(sysTaskId);
        if(sysTask == null){
            throw new BusinessException("流程不存在");
        }
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(sysTask.getProInstId())
                .singleResult();
        if(historicProcessInstance.getEndTime() != null){
            throw new BusinessException("该流程已完结");
        }
        Task task = taskService.createTaskQuery()
                .processInstanceId(sysTask.getProInstId())
                .singleResult();
        if(!userType.equals(task.getName())){
            throw new BusinessException("当前流程待：".concat(task.getName()).concat("审核，你没有审核的权限"));
        }
    }
}
