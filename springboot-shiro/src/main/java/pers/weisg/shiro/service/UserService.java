package pers.weisg.shiro.service;

import pers.weisg.shiro.entities.UserInfo;

import java.util.List;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/6/2
 **/
public interface UserService {

    UserInfo getUser(UserInfo userInfo);

    UserInfo login(String account, String password);

    List<UserInfo> getUserList();
    public void testMethod();
}
