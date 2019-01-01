package pers.weisg.springboot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import pers.weisg.springboot.entity.TUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author weisangeng
 * @since 2018-12-30
 */
public interface TUserService extends IService<TUser> {

    /**
     * 查询用户信息
     * @param username 用户名
     * @return userInfo
     */
    TUser findUserInfo(String username);

}
