package pers.weisg.example.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pers.weisg.example.vo.ProcessVO;

@RestController
public class ProcessController {
    
    @Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private IdentityService identityService;
    
    @Autowired
    private TaskService taskService;

    @RequestMapping("/vac/start")
    public String startVacProcess(Integer days, String reason, HttpSession session) {
        String userId = (String)session.getAttribute("user");
        
        identityService.setAuthenticatedUserId(userId);
        // 启动流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("vacationProcess");
        
        // 完成第一个任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("days", days);
        vars.put("reason", reason);
        taskService.claim(task.getId(), userId);
        taskService.complete(task.getId(), vars);
        return "welcome";
    }
    
    @RequestMapping("/vac/list")
    public String listVac(HttpSession session, Model model) {
        String userId = (String)session.getAttribute("user");
        
        List<ProcessInstance> pis = runtimeService.createProcessInstanceQuery().startedBy(userId).list();
        
        List<ProcessVO> result = new ArrayList<ProcessVO>();
        for(ProcessInstance pi : pis) {
            Integer days = (Integer)runtimeService.getVariable(pi.getId(), "days");
            String reason = (String)runtimeService.getVariable(pi.getId(), "reason");
            System.out.println(reason + "---" + days);
            ProcessVO v = new ProcessVO();
            v.setDays(days);
            v.setReason(reason);
            v.setDate(formatDate(pi.getStartTime()));
            result.add(v);
        }
        model.addAttribute("pis", result);
        return "vac/list";
    }
    
    private static String formatDate(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(d);
        } catch (Exception e) {
            return "";
        }
    }
}
