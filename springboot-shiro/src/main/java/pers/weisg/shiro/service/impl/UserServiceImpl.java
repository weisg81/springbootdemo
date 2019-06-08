package pers.weisg.shiro.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pers.weisg.shiro.entities.UserInfo;
import pers.weisg.shiro.mappers.UserInfoMapper;
import pers.weisg.shiro.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/6/2
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void testMethod() {
        System.out.println("testMethod, time: " + new Date());

        Session session = SecurityUtils.getSubject().getSession();
        Object val = session.getAttribute("key");

        System.out.println("Service SessionVal: " + val);
    }

    @Override
    public UserInfo login(String account, String password) {
        UserInfo userInfoQuery = new UserInfo();
        userInfoQuery.setAccount(account);
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQuery);
        if(validPassword(password,userInfo.getPassword(),account)){
            redisTemplate.opsForValue().set("userInfo",userInfo, 60*60, TimeUnit.SECONDS);
            return userInfo;
        }

        return null;
    }

    private static boolean validPassword(String password,String dbPassword, String username){
        String hashAlgorithmName = "MD5";
        Object salt = ByteSource.Util.bytes(username);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        String encryptionPassword = result.toString();
        if(encryptionPassword.equals(dbPassword)){
            return true;
        }
        return false;
    }

    /*public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        ByteSource salt = ByteSource.Util.bytes("weisg");

        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, "111111", salt, hashIterations);
        String encryptionPassword = result.toString();
        System.out.println(encryptionPassword);
        SimpleHash simpleHash = new SimpleHash("MD5", "weisg", "d2Vpc2c=", 1024);

    }*/

    @Override
    public UserInfo getUser(UserInfo userInfo) {
        return userInfoMapper.selectOne(userInfo);
    }

    @Override
    public List<UserInfo> getUserList() {
        return userInfoMapper.selectAll();
    }
}
