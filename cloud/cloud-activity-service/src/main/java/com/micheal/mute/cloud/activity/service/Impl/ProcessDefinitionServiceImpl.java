package com.micheal.mute.cloud.activity.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.service.IProcessDefinitionService;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.utils.StringUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/6 18:58
 * @Description 流程定义service
 */
@Transactional
@Service
public class ProcessDefinitionServiceImpl implements IProcessDefinitionService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public Page<com.micheal.mute.cloud.activity.domain.ProcessDefinition> listProcessDefinition(com.micheal.mute.cloud.activity.domain.ProcessDefinition processDefinition, PageDomain pageDomain) {
        Page<com.micheal.mute.cloud.activity.domain.ProcessDefinition> page = new Page<>();
        List<com.micheal.mute.cloud.activity.domain.ProcessDefinition> records = page.getRecords();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.orderByDeploymentId().orderByProcessDefinitionVersion().desc();
        if (StringUtils.isNotBlank(processDefinition.getName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + processDefinition.getName() + "%");
        }
        if (StringUtils.isNotBlank(processDefinition.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike("%" + processDefinition.getKey() + "%");
        }
        if (StringUtils.isNotBlank(processDefinition.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike("%" + processDefinition.getCategory() + "%");
        }

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        page.setTotal(processDefinitionQuery.count());
        page.setCurrent(pageDomain.getPageNum());
        page.setSize(pageDomain.getPageSize());

        processDefinitionList.forEach(definition -> {
            ProcessDefinitionEntity entityImpl = (ProcessDefinitionEntity) definition;
            com.micheal.mute.cloud.activity.domain.ProcessDefinition entity = new com.micheal.mute.cloud.activity.domain.ProcessDefinition();
            BeanUtils.copyProperties(definition, entity, "deploymentTime", "suspensionState");
//            entity.setId(definition.getId());
//            entity.setKey(definition.getKey());
//            entity.setName(definition.getName());
//            entity.setCategory(definition.getCategory());
//            entity.setVersion(definition.getVersion());
//            entity.setDescription(definition.getDescription());
//            entity.setDeploymentId(definition.getDeploymentId());
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(entityImpl.getDeploymentId()).singleResult();
            entity.setDeploymentTime(deployment.getDeploymentTime());
//            entity.setDiagramResourceName(definition.getDiagramResourceName());
//            entity.setResourceName(definition.getResourceName());
//            entity.setSuspendState(entityImpl.getSuspensionState() + "");
            entity.setSuspendStateName(entityImpl.getSuspensionState() == 1 ? "已激活": "已挂起");
            records.add(entity);
        });
        return page;
    }

    @Override
    public void deployProcessDefinition(String arg) {

    }

    @Override
    public int deleteProcessDeploymentByIds(String deploymentIds) {
        return 0;
    }

    @Override
    public void suspendOrActiveApply(String id, String suspendState) {

    }
}
