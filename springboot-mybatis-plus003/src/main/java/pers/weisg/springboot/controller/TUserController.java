package pers.weisg.springboot.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weisangeng
 * @since 2018-12-30
 */
@Api(value = "RESTful API ", description = "RESTful API ")
@RestController
@RequestMapping(value = "/api")
public class TUserController extends ApiController {


    @ApiOperation(value = "RESTful API info", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", required = false,
                    dataType = "String", defaultValue = "zyh")
    })
    @RequestMapping(value = "userTest", method = RequestMethod.POST)
    @ResponseBody
    public Object userTest(String name) {

        return success("");
    }
}

