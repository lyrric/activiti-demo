package com.github.lyrric.test.activiti;

import com.github.lyrric.test.activiti.constant.SysConstant;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.lyrric.test.activiti.constant.SysConstant.PROCESS_DEFINITION_KEY;
import static com.github.lyrric.test.activiti.constant.SysConstant.PROCESS_INSTANCE_ID;

/**
 * Created on 2019-05-17.
 * 学生
 * @author wangxiaodong
 */
public class StudentTest {

    Logger logger = LoggerFactory.getLogger(StudentTest.class);

    /**
     * 学生名称
     */
    private static String STUDENT_NAME = "ZhangShan";

    private ProcessEngine processEngine;

    StudentTest(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 创建申请
     */
    @Test
    void createProcess(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //将信息加入map,以便传入流程中
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("studentName", STUDENT_NAME);
        //请假天数
        variables.put("day", 10);
        //开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SysConstant.PROCESS_DEFINITION_KEY, variables);
        //实例流程id,用来记录流程,以便获取当前任务2501
        String processInstanceId = processInstance.getId();
        logger.info("流程开启成功.......实例流程id：" + processInstanceId);
    }

    /**
     * 提交申请
     */
    @Test
    void complete(){
        TaskService taskService = processEngine.getTaskService();
        //获取指定流程ID走到那个步骤
        Task task = taskService.createTaskQuery()
                .processInstanceId(PROCESS_INSTANCE_ID)
                .singleResult();
        Map<String, Object> map = new HashMap<>();
        map.put("createTime", "2019年5月22日 10:51:31");
        if(task.getName().equals("请假申请")){
            //完成任务
            taskService.complete(task.getId(), map);
        }
    }
    /**
     * 列出当前用户所有的流程
     */
    @Test
    void listStudentProcess(){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(PROCESS_DEFINITION_KEY)
                .variableValueEquals("studentName", STUDENT_NAME).list();

        for (HistoricProcessInstance instance : list) {
            Map<String, Object> map = instance.getProcessVariables();
            StringBuilder info = new StringBuilder("instanceId："
                    .concat(instance.getId())
                    .concat("，提交时间：")
                    .concat(instance.getStartTime().toString()));
            //map.forEach((key, value)-> info.append(",").append(key).append("：").append(value.toString()));
            logger.info(info.toString());
        }
    }

}
