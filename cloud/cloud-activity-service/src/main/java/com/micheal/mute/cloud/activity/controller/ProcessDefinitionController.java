package com.micheal.mute.cloud.activity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.domain.AjaxResult;
import com.micheal.mute.cloud.activity.domain.ProcessDefinition;
import com.micheal.mute.cloud.activity.service.IProcessDefinitionService;
import com.micheal.mute.commons.page.PageDomain;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/6 16:48
 * @Description 流程定义相关
 */
@RestController
@RequestMapping("/definition")
@Slf4j
public class ProcessDefinitionController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IProcessDefinitionService processDefinitionService;

    @RequestMapping("/list")
    public AjaxResult list(ProcessDefinition processDefinition, PageDomain pageDomain) {
        Page<ProcessDefinition> processDefinitions = processDefinitionService.listProcessDefinition(processDefinition, pageDomain);
        return AjaxResult.success(processDefinitions);
    }
}
