package com.github.lyrric.service.impl;

import com.github.lyrric.entity.SysTask;
import com.github.lyrric.mapper.SysTaskMapper;
import com.github.lyrric.mapper.TeacherMapper;
import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.TeacherTaskListVO;
import com.github.lyrric.service.TeacherService;
import com.github.pagehelper.PageHelper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019-05-23
 *
 * @author wangxiaodong
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    /**
     * 学生ID
     */
    private final Integer TEACHER_ID = 1;
    /**
     * 学生名称
     */
    private final String TEACHER_NAME = "张三";
    /**
     * 班主任节点名称
     */
    private final String TASK_NAME = "班主任";

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private SysTaskMapper sysTaskMapper;
    @Resource
    private HistoryService historyService;
    @Resource
    private TaskService taskService;
    @Override
    public PageResult<TeacherTaskListVO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageResult<>(teacherMapper.list(TEACHER_ID));
    }

    @Override
    public void approval(Integer id) throws BusinessException {
        SysTask sysTask = sysTaskMapper.selectByPrimaryKey(id);
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
        if(!TASK_NAME.equals(task.getName())){
            throw new BusinessException("当前流程待：".concat(task.getName()).concat("审核，你没有审核的权限"));
        }
        Map<String, Object> var = new HashMap<>(1);
        var.put("teacherId", TEACHER_ID);
        var.put("teacherName", TEACHER_NAME);
        taskService.complete(task.getId(), var);
    }
}
