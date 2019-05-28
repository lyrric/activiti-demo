package com.github.lyrric.command;

import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2019-05-28.
 *
 * @author wangxiaodong
 */
public class JumpToTargetNodeCmd implements Command<Void>{

    private Logger logger = LoggerFactory.getLogger(JumpToTargetNodeCmd.class);
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 要跳转到的节点ID
     */
    private String targetNodeId;

    public JumpToTargetNodeCmd(String taskId, String targetNodeId) {
        this.taskId = taskId;
        this.targetNodeId = targetNodeId;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        logger.info("跳转到流程节点：".concat(targetNodeId));
        TaskEntityManager taskEntityManager = commandContext.getTaskEntityManager();
        //获取任务的来源任务和来源节点信息
        TaskEntity taskEntity = taskEntityManager.findById(taskId);
        ExecutionEntity executionEntity =taskEntity.getExecution();
        Process process = ProcessDefinitionUtil.getProcess(executionEntity.getProcessDefinitionId());
        //删除当前节点
        taskEntityManager.deleteTask(taskEntity,"", true, true);
        //获取要跳转的目标节点
        FlowElement flowElement = process.getFlowElement(targetNodeId);
        executionEntity.setCurrentFlowElement(flowElement);
        ActivitiEngineAgenda agenda = commandContext.getAgenda();
        agenda.planContinueProcessInCompensation(executionEntity);
        return null;
    }
}
