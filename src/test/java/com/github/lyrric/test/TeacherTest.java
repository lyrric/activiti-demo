package com.github.lyrric.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2019-05-21.
 * 教师
 * @author wangxiaodong
 */
public class TeacherTest {
    /**
     * 教师名称
     */
    public static String TEACHER_NAME = "LiSi";

    Logger logger = LoggerFactory.getLogger(StudentTest.class);

    private ProcessEngine processEngine;

    TeacherTest(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    @Test
    void listTeacherProcess(){
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .variableValueEquals("teacherName", TEACHER_NAME)
                .list();
        for (HistoricVariableInstance instance : list) {
            String info = "id："
                    .concat(instance.getId())
                    .concat("，学生：")
                    .concat(instance.getValue().toString())
                    .concat("，提交时间：")
                    .concat(instance.getCreateTime().toString());
            logger.info(info);
        }
    }
    //开始走流程
    @Test
    void getTaskAndComplete(){
        String processInstanceId = "2501";
        TaskService taskService = processEngine.getTaskService();
        //获取查询结果,如果为空,说明这个流程已经执行完毕,否则,获取任务并执行
        if(processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult() == null){
            System.out.println("流程已结束");
            return ;
        }
        //查询
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .processVariableValueEquals("studentName", "张三")
                .singleResult();
        String taskName = task.getName();
        System.out.println(taskName);
        Map<String, Object> variables = new HashMap<>();
        //完成任务
        if(taskName.equals("请假申请")){
            variables.put("applyTime", "s2019年5月21日 11:29:19");
            System.out.println("职员已经提交申请.......");
        }else if(taskName.equals("班主任")){
            variables.put("comment", "王：同意审批");
            variables.put("teacher", "王老师");
            System.out.println("王老师已同意审批.......");
        }else{
            variables.put("comment", "李：同意审批");
            variables.put("leader", "李经理");
            System.out.println("李经理已同意审批.......");
        }

        taskService.complete(task.getId(), variables);
    }

}
