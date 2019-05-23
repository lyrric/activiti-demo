package com.github.lyrric.test.activiti;

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

import static com.github.lyrric.test.activiti.constant.SysConstant.PROCESS_DEFINITION_KEY;

/**
 * Created on 2019-05-21.
 * 教务处领导
 * @author wangxiaodong
 */
public class LeaderTest {

    Logger logger = LoggerFactory.getLogger(StudentTest.class);

    /**
     * 教务处领导
     */
    private static String LEADER_NAME = "WangWu";

    private ProcessEngine processEngine;

    LeaderTest(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 查询（WangWu）已处理的流程
     */
    @Test
    void listTeacherProcessed(){
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .variableValueEquals("leaderName", LEADER_NAME)
                .list();
        for (HistoricVariableInstance instance : list) {
            String info = "（WangWu）已处理的流程,id："
                    .concat(instance.getId())
                    .concat("，提交时间：")
                    .concat(instance.getCreateTime().toString());
            logger.info(info);
        }
    }
    /**
     * 查询走到教务处的流程(未处理)
     */
    @Test
    void listUnprocessed(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(PROCESS_DEFINITION_KEY)
                .taskName("教务处")
                .list();
        for (Task task : tasks) {
            StringBuilder stringBuilder = new StringBuilder("走到教务处的流程(未处理),ProcessInstanceId="
                    .concat(task.getProcessInstanceId())
                    .concat("，TaskId=")
                    .concat(task.getId()));
            Map<String, Object> map = task.getProcessVariables();
            map.forEach((key, value)->
                    stringBuilder
                            .append("，key=")
                            .append(key)
                            .append("，value=")
                            .append(value.toString())
            );
            logger.info(stringBuilder.toString());
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
            logger.info("流程已结束");
            return ;
        }
        //查询
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskName("教务处")
                .singleResult();
        Map<String, Object> variables = new HashMap<>();
        //完成任务
        variables.put("comment", "李：同意审批");
        variables.put("leaderName", LEADER_NAME);
        taskService.complete(task.getId(), variables);
        logger.info("李经理已同意审批.......");

    }
}
