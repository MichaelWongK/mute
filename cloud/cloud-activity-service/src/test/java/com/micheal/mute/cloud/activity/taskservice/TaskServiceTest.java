package com.micheal.mute.cloud.activity.taskservice;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/18 16:33
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void taskTest() {
        TaskQuery taskQuery = taskService.createTaskQuery();
//        taskQuery.taskCandidateUser("axianlu").list();
//        taskQuery.taskAssignee("axianlu").list();
         taskQuery.processDefinitionKey("leave").taskCandidateOrAssigned("axianlu").list();
//         taskQuery.taskAssigneeIds(Arrays.asList("axianlu".split(" ")));
                taskQuery.taskCandidateOrAssigned("axianlu").taskCandidateGroupIn(Arrays.asList("deptLeader".split(" "))).list();
    }
}
