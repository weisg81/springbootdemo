package com.kk.server.controller;


import com.alibaba.fastjson.JSONObject;
import com.kk.server.kk.po.user.UserInfo;
import com.kk.server.kk.service.user.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/api")
public class UserInfoController{

    @Autowired
    private UserInfoService userInfoService;


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object userTest(Long userId) {
        UserInfo userInfo = userInfoService.findUserInfo(userId);
        System.out.println(userInfo.getName());

        log.info(JSONObject.toJSONString(userInfo));
        return userInfo;
    }
}

