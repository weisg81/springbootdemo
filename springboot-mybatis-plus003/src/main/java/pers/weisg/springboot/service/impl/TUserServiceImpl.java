package pers.weisg.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.weisg.springboot.entity.TUser;
import pers.weisg.springboot.mapper.TUserMapper;
import pers.weisg.springboot.service.TUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author weisangeng
 * @since 2018-12-30
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {
    @Override
    public TUser findUserInfo(String username) {
        TUser user = new TUser();
        QueryWrapper query = new QueryWrapper<>(user);;
        this.getOne(query);
        return null;
    }
}
