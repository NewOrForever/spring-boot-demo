package com.example.boot;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;

/**
 * ClassName:BasicDemo
 * Package:com.example.boot
 * Description:
 *
 * @Date:2022/10/19 16:12
 * @Author:qs@1.com
 */
public class BasicDemo {
    public static void main(String[] args) {
        createTable();
    }

    public static void createTable() {
        //默认创建方式
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通用的创建方式，指定配置文件名和Bean名称
        // ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml", " processEngineConfiguration");
        // ProcessEngine processEngine1 = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);

    }
}
