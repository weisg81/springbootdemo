package pers.weisg.example.service;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.weisg.example.common.bean.HashMapResult;

/** 
 * @Description: TODO(用一句话描述该文件做什么)
 * @author WEISANGNG   
 * @date 2019年5月2日   
 */
@Service
public class OATaskService {
	private static final Logger logger = LoggerFactory.getLogger(OATaskService.class);

	@Autowired
	private TaskService taskService;

	public HashMapResult complete(String processInstanceId, String comment, String expression) {
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		if(task == null) {
			logger.error("process {} task is not exists", processInstanceId);
			return HashMapResult.failure("流程任务不存在");
		}
		if(StringUtils.isNotEmpty(comment)) {
			taskService.addComment(task.getId(), processInstanceId, comment);
		}
		Map<String, Object> variables = new HashMap<>();
		variables.put("expression", expression);

		taskService.complete(task.getId(), variables);

		task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		HashMapResult result = HashMapResult.success();
		result.put("isFinished", task == null);
		return result;
	}
}
