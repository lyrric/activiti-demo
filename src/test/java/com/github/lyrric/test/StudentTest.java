package com.github.lyrric.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
     * 开启流程
     */
    @Test
    void startProcess(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processKey = "leave_process";
        //将信息加入map,以便传入流程中
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("studentName", STUDENT_NAME);
        //请假天数
        variables.put("day", 10);
        //开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);
        //实例流程id,用来记录流程,以便获取当前任务2501
        String processInstanceId = processInstance.getId();
        logger.info("流程开启成功.......实例流程id：" + processInstanceId);
    }

    /**
     * 列出当前用户所有的经常
     */
    @Test
    void listAll(){


    }
}
