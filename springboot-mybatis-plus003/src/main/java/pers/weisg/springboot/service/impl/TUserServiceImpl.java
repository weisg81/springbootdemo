package pers.weisg.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TUserMapper tUserMapper;
    @Override
    public TUser findUserInfo(String userId) {
        //QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        /*TUser user = new TUser();
        QueryWrapper query = new QueryWrapper<>(user);;
        this.getOne(query);*/
        //wrapper.lambda().eq(TUser::getId,"");
        return tUserMapper.selectById(userId);
    }
}
