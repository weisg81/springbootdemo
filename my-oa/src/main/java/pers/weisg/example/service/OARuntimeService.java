package pers.weisg.example.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import pers.weisg.example.common.bean.HashMapResult;

/** 
 * @Description: TODO(用一句话描述该文件做什么)
 * @author WEISANGNG   
 * @date 2019年5月2日   
 */
@Service
public class OARuntimeService {
	private static final Logger logger = LoggerFactory.getLogger(OARuntimeService.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private TaskService taskService;
	
	/**
	 * @Description:启动流程实例
	 * @param userId
	 * @param processDefinitionId
	 * @param variables
	 * @return 
	 * @author WEISANGNG
	 * @date 2019年5月2日
	 */
	public HashMapResult startProcess(String userId, String processDefinitionId, Map<String, Object> variables) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		if(processDefinition == null) {
			logger.error("process definition {} is not exists", processDefinitionId);
			return HashMapResult.failure("流程定义" + processDefinitionId + "不存在");
		}

		identityService.setAuthenticatedUserId(userId);
		ProcessInstance processInstance;
		if(!CollectionUtils.isEmpty(variables)) {
			processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
		} else {
			processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
		}

		HashMapResult result = HashMapResult.success();
		result.put("processInstanceId", processInstance.getId());
		return result;
	}
	
	/**
	 * @Description:查询流程实例表，查询的表是：act_ru_execution
	 * @return 
	 * @author WEISANGNG
	 * @date 2019年5月2日
	 */
	public HashMapResult queryProcessInstance() {
		ProcessInstanceQuery createProcessInstanceQuery = runtimeService.createProcessInstanceQuery();
		//createProcessInstanceQuery.processDefinitionId(processDefinitionId)
		
		return null;
	}
	
	public HashMapResult findProcessByUserOrGroup(String userId, String groupId) {
		if(StringUtils.isEmpty(userId) && StringUtils.isEmpty(groupId)) {
			return HashMapResult.failure("查询参数错误");
		}

		Set<String> processInstanceIdSet = new HashSet<>();

		if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(groupId)) {
			//TODO 查询待优化
			List<Task> taskList1 = taskService.createTaskQuery().taskOwner(userId).list();
			List<Task> taskList2 = taskService.createTaskQuery().taskAssignee(userId).list();
			List<Task> taskList3 = taskService.createTaskQuery().taskCandidateUser(userId).list();
			List<Task> taskList4 = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(groupId)).list();
			for (Task task : taskList1) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
			for (Task task : taskList2) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
			for (Task task : taskList3) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
			for (Task task : taskList4) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
		}
		if(StringUtils.isNotEmpty(userId) && StringUtils.isEmpty(groupId)) {
			List<Task> taskList1 = taskService.createTaskQuery().taskOwner(userId).list();
			List<Task> taskList2 = taskService.createTaskQuery().taskAssignee(userId).list();
			List<Task> taskList3 = taskService.createTaskQuery().taskCandidateUser(userId).list();
			for (Task task : taskList1) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
			for (Task task : taskList2) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
			for (Task task : taskList3) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
		}
		if(StringUtils.isEmpty(userId) && StringUtils.isNotEmpty(groupId)) {
			List<Task> taskList = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(groupId)).list();
			for (Task task : taskList) {
				processInstanceIdSet.add(task.getProcessInstanceId());
			}
		}

		String processInstanceId = "";
		if(!CollectionUtils.isEmpty(processInstanceIdSet)) {
			processInstanceId = StringUtils.join(processInstanceIdSet, ",");
		}

		HashMapResult result = HashMapResult.success();
		result.put("processInstanceId", processInstanceId);

		return result;
	}

	public HashMapResult cancel(String processInstanceId, String reason) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(processInstance == null) {
			logger.error("process instance {} is not exists", processInstanceId);
			return HashMapResult.failure("流程实例不存在");
		}
		runtimeService.deleteProcessInstance(processInstanceId, reason);
		return HashMapResult.success();
	}

}
