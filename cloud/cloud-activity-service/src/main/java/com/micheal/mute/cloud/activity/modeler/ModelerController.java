package com.micheal.mute.cloud.activity.modeler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.Page;
import com.micheal.mute.cloud.activity.domain.AjaxResult;
import com.micheal.mute.commons.dto.ResponseResult;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/4/30 17:06
 * @Description 模型相关控制器
 */
@RestController
@RequestMapping("modeler")
@Slf4j
public class ModelerController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询所有模型
     * @param modelEntity
     * @return
     */
    @RequestMapping("/list")
    public ResponseResult list(ModelEntityImpl modelEntity, Integer isDeployed, PageDomain page) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.orderByLastUpdateTime().desc();

        // 条件过滤 key
        if (StringUtils.isNotBlank(modelEntity.getKey())) {
            modelQuery.modelKey(modelEntity.getKey());
        }
        if (StringUtils.isNotBlank(modelEntity.getName())) {
            modelQuery.modelNameLike("%" + modelEntity.getName() + "%");
        }
        if (StringUtils.isNotNull(isDeployed)) {
            if (isDeployed == 0) {
                modelQuery.deployed();
            } else {
                modelQuery.notDeployed();
            }
        }
        List<Model> resultList = modelQuery.listPage(page.getPageNum() - 1, page.getPageSize());

        Page<Model> list = new Page<>();
        list.addAll(resultList);
        list.setTotal(modelQuery.count());
        list.setPageNum(page.getPageNum());
        list.setPageSize(page.getPageSize());

        return new ResponseResult(ResponseResult.CodeStatus.OK, list);

    }

    /**
     * 创建模型
     * @param name 模型名称
     * @param key  模型key
     * @param description   模型描述
     * @return
     */
    @RequestMapping("/create")
    public AjaxResult create(@RequestParam("name") String name, @RequestParam("key") String key,
                             @RequestParam(value = "description", required = false) String description) {
        try {
            log.info("创建模型入参name：{},key:{}", name, key);
            Model model = repositoryService.newModel();
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, org.apache.commons.lang3.StringUtils.defaultString(description));
            model.setMetaInfo(modelObjectNode.toString());
            model.setName(name);
            model.setKey(org.apache.commons.lang3.StringUtils.defaultString(key));

            log.info("创建模型完善ModelEditorSource入参模型ID{}", model.getId());
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.set("stencilset", stencilSetNode);
            log.info("创建模型完善ModelEditorSource结束");

            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
            return new AjaxResult(AjaxResult.Type.SUCCESS, "创建模型成功", model.getId());
        } catch (Exception e) {
            log.error("创建模型失败: ", e);
        }

        return AjaxResult.error();
    }

    /**
     * 部署流程模型
     * @param modelId
     * @return
     */
    @RequestMapping("/deploy/{modelId}")
    public AjaxResult deploy(@PathVariable("modelId") String modelId) {
        try {
            log.info("流程部署入参modelId：{}", modelId);
            Model modelData = repositoryService.getModel(modelId);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
            if (bytes == null) {
                throw new ActivitiException("模型数据为空，请先设计流程并成功保存，再进行发布");
            }
            JsonNode jsonNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
                    .addBpmnModel(modelData.getName() + ".bpmn20.xml", model)
                    .deploy();
            log.info("流程部署成功，部署ID：{}", deployment.getId());
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            return AjaxResult.success("流程模型部署成功");
        } catch (IOException e) {
            log.info("部署modelId:{}模型服务异常：{}", modelId, e);
        }
        return AjaxResult.error("流程模型部署失败");
    }

    @DeleteMapping("/remove/{modelId}")
    public AjaxResult remove(@PathVariable("modelId") String modelId) {
        try {
            repositoryService.deleteModel(modelId);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

}
