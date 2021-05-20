package com.micheal.mute.cloud.activity.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.dto.ProcessDefinitionDto;
import com.micheal.mute.cloud.activity.service.IProcessDefinitionService;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.utils.StringUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public Page<ProcessDefinitionDto> listProcessDefinition(ProcessDefinitionDto processDefinitionDto, PageDomain pageDomain) {
        Page<ProcessDefinitionDto> page = new Page<>();
        List<ProcessDefinitionDto> records = new ArrayList<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.orderByDeploymentId().orderByProcessDefinitionVersion().desc();
        if (StringUtils.isNotBlank(processDefinitionDto.getName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + processDefinitionDto.getName() + "%");
        }
        if (StringUtils.isNotBlank(processDefinitionDto.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike("%" + processDefinitionDto.getKey() + "%");
        }
        if (StringUtils.isNotBlank(processDefinitionDto.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike("%" + processDefinitionDto.getCategory() + "%");
        }

        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        page.setTotal(processDefinitionQuery.count());
        page.setCurrent(pageDomain.getPageNum());
        page.setSize(pageDomain.getPageSize());

        processDefinitionList.forEach(definition -> {
            ProcessDefinitionEntity entityImpl = (ProcessDefinitionEntity) definition;
            ProcessDefinitionDto entity = new ProcessDefinitionDto();
            BeanUtils.copyProperties(definition, entity, "deploymentTime");
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
            entity.setSuspendState(String.valueOf(entityImpl.getSuspensionState()));
            entity.setSuspendStateName(entityImpl.getSuspensionState() == 1 ? "已激活": "已挂起");
            records.add(entity);
        });
        return page.setRecords(records);
    }

    @Override
    public void deployProcessDefinition(String arg) {

    }

    @Override
    public int deleteProcessDeploymentByIds(String deploymentIds) throws Exception {
        String[] deploymentIdsArr = deploymentIds.split(",");
        int count = 0;
        for (String deploymentId : deploymentIdsArr) {
            List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery()
                    .deploymentId(deploymentId)
                    .list();
            if (!CollectionUtils.isEmpty(instanceList)) {
                throw new Exception("删除失败，存在运行中的流程实例");
            }
            repositoryService.deleteDeployment(deploymentId, true); // true 表示级联删除引用，比如 act_ru_execution 数据
            count++;
        }

        return count;
    }

    @Override
    public void suspendOrActiveApply(String id, String suspendState) {
        if ("1".equals(suspendState)) {
            // 当流程定义被挂起时，已经发起的该流程定义的流程实例不受影响（如果选择级联挂起则流程实例也会被挂起）。
            // 当流程定义被挂起时，无法发起新的该流程定义的流程实例。
            // 直观变化：act_re_procdef 的 SUSPENSION_STATE_ 为 2
            repositoryService.suspendProcessDefinitionById(id);
        } else if ("2".equals(suspendState)) {
            repositoryService.activateProcessDefinitionById(id);
        }
    }
}
