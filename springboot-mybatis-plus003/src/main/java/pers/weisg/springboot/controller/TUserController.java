package pers.weisg.springboot.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.ApiController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.weisg.springboot.entity.TUser;
import pers.weisg.springboot.service.TUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weisangeng
 * @since 2018-12-30
 */
@Slf4j
@Api(value = "RESTful API ", description = "RESTful API ")
@RestController
@RequestMapping(value = "/api")
public class TUserController extends ApiController {

    @Autowired
    private TUserService tUserService;


    @ApiOperation(value = "RESTful API info", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true,
                    dataType = "String", defaultValue = "")
    })
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object userTest(String userId) {
        TUser userInfo = tUserService.findUserInfo(userId);
        System.out.println(userInfo.getName());

        log.info(JSONObject.toJSONString(userInfo));
        return success(userInfo);
    }
}

