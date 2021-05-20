package com.micheal.mute.cloud.activity.controller;

import com.micheal.mute.cloud.activity.domain.HistoricActivity;
import com.micheal.mute.cloud.activity.service.IProcessService;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.provider.domain.AjaxResult;
import com.micheal.mute.commons.utils.IOUtils;
import com.micheal.mute.provider.api.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/8 17:27
 * @Description 具体流程相关操作
 */
@Controller
@RequestMapping("/process")
@Slf4j
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IProcessService processService;

    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;

    /**
     * 发起流程
     *
     * @param applyUserId
     * @param businessKey
     * @param itemName
     * @param itemContent
     * @param key
     * @return
     */
    @PostMapping("/submitApply")
    @ResponseBody
    public AjaxResult submitApply(String applyUserId, String businessKey, String itemName, String itemContent, String key) {
        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, itemName, itemContent, key, new HashMap<>());
        return AjaxResult.success(processInstance.getId());
    }

    /**
     * 完成任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/complete/{taskId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult complete(@PathVariable("taskId") String taskId, String username, String instanceId, String bizTitle, String bizReason, String module, HttpServletRequest request) {
        processService.complete(username, taskId, instanceId, bizTitle, bizReason, module, new HashMap<>(), request);
        return AjaxResult.success("任务已完成");
    }

    /**
     * 审批历史
     * @param instanceId
     * @param historicActivity
     * @param pageDomain
     * @return
     */
    @PostMapping("/listHistory")
    @ResponseBody
    public AjaxResult listHistory(String instanceId, HistoricActivity historicActivity, PageDomain pageDomain) {
        return AjaxResult.success(processService.selectHistoryList(instanceId, historicActivity, pageDomain));
    }

    /**
     * 读取流程实例流程图片
     *
     * @param instanceId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/read-resource")
    public void readResource(String instanceId, HttpServletResponse response) throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceId).singleResult();

        String processDefinitionId ;
        if (processInstance == null) {
            log.info("对应流程实例id：{}为空，准备查询历史流程实例", instanceId);
            //查询已经结束的流程实例
            HistoricProcessInstance processInstanceHistory = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            if (processInstanceHistory == null) {
                throw new Exception("对应查询条件下无流程实例或历史流程实例");
            } else {
                processDefinitionId = processInstanceHistory.getProcessDefinitionId();
            }
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }

        String fontName = "宋体";
        //获取BPMN模型对象
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        //获取流程实例当前的节点，需要高亮显示
        List<String> currentActs = Collections.emptyList();
        if (processInstance != null) {
            currentActs = runtimeService.getActiveActivityIds(processInstance.getId());
        }
        InputStream png = processEngine.getProcessEngineConfiguration()
                .getProcessDiagramGenerator()
                .generateDiagram(model, "png", currentActs, new ArrayList<>(),
                        fontName, fontName, fontName, null, 1.0);

        response.setContentType("image/png");

        IOUtils.OutputResponse(response, png);
    }

    /**
     * 委托任务
     *
     * @param taskId 任务id
     * @param username 委托人
     * @param delegateToUser 被委托人
     * @return
     */
    @PostMapping("/delegate")
    @ResponseBody
    public AjaxResult delegate(String taskId, String username, String delegateToUser) {
        processService.delegate(taskId, username, delegateToUser);
        return AjaxResult.success();
    }

    @PostMapping("/cancelApply")
    @ResponseBody
    public AjaxResult cancelApply(String instanceId) {
        processService.cancelApply(instanceId, "用户撤销");
        return AjaxResult.success();
    }

    @PostMapping("/suspendOrActiveApply")
    @ResponseBody
    public AjaxResult suspendOrActiveApply(String instanceId, String suspendState) {
        processService.suspendOrActiveApply(instanceId, suspendState);
        return AjaxResult.success();
    }

}
