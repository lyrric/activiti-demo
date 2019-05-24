package com.github.lyrric.service.impl;

import com.github.lyrric.entity.SysTask;
import com.github.lyrric.mapper.StudentMapper;
import com.github.lyrric.mapper.SysTaskMapper;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.StudentTaskListVO;
import com.github.lyrric.service.StudentService;
import com.github.pagehelper.PageHelper;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.github.lyrric.constant.SysConstant.PROCESS_DEFINITION_KEY;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    /**
     * 学生ID
     */
    private final Integer STUDENT_ID = 1;
    /**
     * 学生名称
     */
    private final String STUDENT_NAME = "张三";
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private SysTaskMapper sysTaskMapper;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Date startDate, Integer day) {
        Map<String, Object> var = new HashMap<>();
        var.put("studentId", STUDENT_ID);
        //创建流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, var);
        logger.info("创建流程成功，流程ID：".concat(processInstance.getId()));
        //创建流程后，还并没有提交申请，需要找到对应的第一个节点（提交审批节点），提交后，才会走到班主任节点
        //搜索流程走到哪一个任务,因为流程没有分支，所以不会返回多个任务
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        //提交申请
        taskService.complete(task.getId());
        //保存到业务数据库
        SysTask sysTask = new SysTask();
        sysTask.setCreatedTime(new Date());
        sysTask.setDay(day);
        sysTask.setStartTime(startDate);
        sysTask.setStudentId(STUDENT_ID);
        sysTask.setStudentName(STUDENT_NAME);
        sysTask.setProInstId(processInstance.getId());
        sysTaskMapper.insert(sysTask);
        logger.info("请教申请提交成功，ID：".concat(sysTask.getId().toString()));
    }

    @Override
    public PageResult<StudentTaskListVO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageResult<>(studentMapper.list());
    }
}
