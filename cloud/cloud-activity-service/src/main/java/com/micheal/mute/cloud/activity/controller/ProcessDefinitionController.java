package com.micheal.mute.cloud.activity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.micheal.mute.commons.provider.controller.BaseController;
import com.micheal.mute.commons.provider.domain.AjaxResult;
import com.micheal.mute.cloud.activity.domain.ProcessDefinitionDto;
import com.micheal.mute.cloud.activity.service.IProcessDefinitionService;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.utils.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/6 16:48
 * @Description 流程定义相关
 */
@Controller
@RequestMapping("/definition")
@Slf4j
public class ProcessDefinitionController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IProcessDefinitionService processDefinitionService;

    /**
     * 查询所有流程定义
     * @param processDefinitionDto
     * @param pageDomain
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public AjaxResult list(ProcessDefinitionDto processDefinitionDto, PageDomain pageDomain) {
        Page<ProcessDefinitionDto> processDefinitions = processDefinitionService.listProcessDefinition(processDefinitionDto, pageDomain);
        return AjaxResult.success(processDefinitions);
    }

    /**
     * 部署流程定义
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(@RequestParam("processDefinition") MultipartFile file) {
        // TODO 导入流程定义并部署待补充
        return null;
    }

    /**
     * 根据deploymentIds删除流程部署
     * @param ids
     * @return
     */
    @DeleteMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam("ids") String ids) {
        try {
            return toAjax(processDefinitionService.deleteProcessDeploymentByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 挂起或激活流程定义
     * @param id
     * @param suspendState
     * @return
     */
    @PostMapping("/suspendOrActiveApply")
    @ResponseBody
    public AjaxResult suspendOrActiveApply(String id, String suspendState) {
        processDefinitionService.suspendOrActiveApply(id, suspendState);
        return success();
    }

    /**
     * 通过流程定义id&&资源名称读取流程定义详情
     * @param processDefinitionId
     * @param resourceName
     * @param response
     */
    @PostMapping("/readProcessDefinition")
    public void readProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceName") String resourceName, HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

        // 读取资源
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        IOUtils.OutputResponse(response, resourceAsStream);
    }

    /**
     * 流程定义转换为流程模型
     * @param processDefinitionId
     * @return
     */
    @PostMapping(value = "/convertToModel")
    @ResponseBody
    public AjaxResult convertToModel(@RequestParam("processDefinitionId") String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr); // 转换为bpmnModel

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);            // 转换为jsonModel

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());

        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getName());
        modelData.setCategory(processDefinition.getDeploymentId());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("UTF-8"));
        return success();
    }
}

