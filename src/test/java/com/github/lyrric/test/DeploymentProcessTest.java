package com.github.lyrric.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;

/**
 * Created on 2019-05-17.
 *
 * @author wangxiaodong
 */
public class DeploymentProcessTest {


    private ProcessEngine processEngine;
    DeploymentProcessTest(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 部署流程
     */
    @Test
    void deploymentProcess(){
        org.activiti.engine.repository.Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程")
                .addClasspathResource("leaveProcess.bpmn")
                .addClasspathResource("leaveProcess.png")
                .deploy();
        //1
        System.out.println("部署工程id:"+deployment.getId());
        //请假流程
        System.out.println("部署工程name:"+deployment.getName());
    }
}
