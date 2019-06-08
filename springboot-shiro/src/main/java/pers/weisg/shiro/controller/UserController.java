package pers.weisg.shiro.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pers.weisg.shiro.entities.UserInfo;
import pers.weisg.shiro.service.UserService;
import pers.weisg.shiro.utils.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/6/2
 **/
@Api(description = "用户", value = "/user", protocols = "HTTP")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功", response = HashMap.class),
            @ApiResponse(code = 500, message = "服务器内部异常", response = Map.class)})
    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        session.setAttribute("key", "value12345");
        userService.testMethod();
        return "redirect:/list.jsp";
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功", response = HashMap.class),
            @ApiResponse(code = 500, message = "服务器内部异常", response = Map.class)})
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(ServletRequest request, HttpServletResponse response, String account, String password) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("code",0);
        retMap.put("msg", "success");
        try {


            UserInfo userInfo = userService.login(account, password);
            if(userInfo != null){
                retMap.put("userInfo", userInfo);
                retMap.put("token", JwtUtil.createToken(userInfo.getUserId(), userInfo.getAccount(),password));
                // 创建token,并存放redis
            }else{
                retMap.put("code","999999");
                retMap.put("msg", "用户不存在！");
            }
        } catch (DisabledAccountException e) {
            request.setAttribute("msg", "账户已被禁用");
            return retMap;
        } catch (AuthenticationException e) {
            request.setAttribute("msg", "用户名或密码错误");
            return retMap;
        }

        // 执行到这里说明用户已登录成功
        return retMap;
    }

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功", response = HashMap.class),
            @ApiResponse(code = 500, message = "服务器内部异常", response = Map.class)})
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserList() {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("code",0);
        retMap.put("msg", "success");
        List<UserInfo> userList = userService.getUserList();
        retMap.put("userList", userList);
        return retMap;
    }

    @ApiOperation(value = "testSwagger", notes = "testSwagger", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功", response = HashMap.class),
            @ApiResponse(code = 500, message = "服务器内部异常", response = Map.class)})
    @RequestMapping(value = "/testSwagger", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> testSwagger(@RequestBody UserInfo userInfo) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("code",0);
        retMap.put("msg", "success");
        retMap.put("userInfo", userInfo);
        return retMap;
    }
}
