package pers.weisg.shiro.secutity;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import pers.weisg.shiro.entities.UserInfo;
import pers.weisg.shiro.jwt.JwtToken;
import pers.weisg.shiro.utils.JwtUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/5/12
 **/
public class CustomRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //authorizationInfo.setRoles(userSession.getRoles());
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("aa");
        roleSet.add("bb");
        authorizationInfo.setRoles(roleSet);
        Set<String> identifierSet = new HashSet<String>();
        identifierSet.add("auth:resource:addResource");
        identifierSet.add("auth:resource:deleteByResIds");
        //authorizationInfo.setStringPermissions(userSession.getPermissions());
        authorizationInfo.setStringPermissions(identifierSet);
        return authorizationInfo;
    }

    /*public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "111111";
        Object salt = ByteSource.Util.bytes("weisg81");
        int hashIterations = 1024;

        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashItions);
        System.out.println(result);
    }*/




    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------"+authenticationToken);
        JwtToken jwtToken = (JwtToken)authenticationToken;
        logger.info("---- 执行 Shiro 凭证认证 --"+authenticationToken.getCredentials());
        logger.info("---- 执行 Shiro 凭证认证 --"+authenticationToken.getPrincipal());
        Long userId = jwtToken.getUserId();
        String username = jwtToken.getUsername();
        String token = jwtToken.getToken();
        JwtUtil.verifyToken(token, userId);
        UserInfo userInfo = (UserInfo)redisTemplate.opsForValue().get("userInfo");
        if(userInfo != null){
            ByteSource credentialsSalt = ByteSource.Util.bytes(username);
            SimpleAuthenticationInfo simpleAuthenticationInfo =
                    new SimpleAuthenticationInfo(userInfo.getAccount(),
                            userInfo.getPassword(), credentialsSalt, super.getName());
            //SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userInfo, token, super.getName());
            return simpleAuthenticationInfo;
        }


        /*UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        Object credentials1 = authenticationToken.getCredentials();
        System.out.println(credentials1);
        // 解密获得username，用于和数据库进行对比
        //String username = JwtUtil.getUsername(tokenCredentials);


        String name = usernamePasswordToken.getUsername();

        //String password = String.valueOf(token.getPassword());
        SysUser user = new SysUser();
        user.setUserName(name);
        //user.setPassWord(password);
        // 从数据库获取对应用户名密码的用户
        SysUser sysUser = sysUserMapper.selectOne(user);
        if(sysUser != null){
            // 用户为禁用状态
            if (!"1".equals(sysUser.getUserEnable())) {
                throw new DisabledAccountException();
            }
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            //1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
            Object principal = sysUser.getUserName();
            //2). credentials: 密码.
            Object credentials = sysUser.getPassWord();
            //3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
            String realmName = getName();
            //4). 盐值.
            ByteSource credentialsSalt = ByteSource.Util.bytes(sysUser.getUserName());

            SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
            info = new SimpleAuthenticationInfo(sysUser, credentials, credentialsSalt, realmName);
            return info;
        }*/
        throw new UnknownAccountException();
    }
}
