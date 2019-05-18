package com.kk.server.kk.service.user.impl;

import com.kk.server.kk.dao.mapper.UserInfoMapper;
import com.kk.server.kk.po.user.UserInfo;
import com.kk.server.kk.service.user.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/5/18
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo findUserInfo(Long userId) {
        return userInfoMapper.selectById(userId);
    }
}
