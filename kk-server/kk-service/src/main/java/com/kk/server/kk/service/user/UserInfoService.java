package com.kk.server.kk.service.user;

import com.kk.server.kk.po.user.UserInfo;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/5/18
 **/
public interface UserInfoService {

    UserInfo findUserInfo(Long userId);
}
