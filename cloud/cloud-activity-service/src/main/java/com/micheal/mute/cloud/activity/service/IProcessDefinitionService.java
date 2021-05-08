package com.micheal.mute.cloud.activity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.domain.ProcessDefinitionDto;
import com.micheal.mute.commons.page.PageDomain;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/6 17:27
 * @Description
 */
public interface IProcessDefinitionService {

    /**
     * 分页查询流程定义文件
     * @param processDefinitionDto
     * @return
     */
    Page<ProcessDefinitionDto> listProcessDefinition(ProcessDefinitionDto processDefinitionDto, PageDomain pageDomain);

    void deployProcessDefinition(String arg);

    /**
     * 通过部署id批量删除流程定义
     * @param deploymentIds
     * @return
     */
    int deleteProcessDeploymentByIds(String deploymentIds) throws Exception;

    /**
     * 挂起/激活流程定义
     * @param id
     * @param suspendState
     */
    void suspendOrActiveApply(String id, String suspendState);
}
