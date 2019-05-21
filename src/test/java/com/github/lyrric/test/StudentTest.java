package com.github.lyrric.test;

import com.github.lyrric.test.constant.SysConstant;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
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
     * 开启一个新的流程
     */
    @Test
    void startProcess(){
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
     * 列出当前用户所有的流程
     */
    @Test
    void listStudentProcess(){
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .variableValueEquals("studentName", STUDENT_NAME)
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

    /**
     * 流程召回（需要手写代码，操作数据库）
     */
    @Test
    void cancelProcess(){
        processEngine.getRuntimeService();
    }
}
