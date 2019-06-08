package pers.weisg.shiro.filter;

import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import pers.weisg.shiro.jwt.JwtToken;
import pers.weisg.shiro.utils.JwtUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Description 无状态授权过滤器，主要校验：
 *              1.请求是否包含token
 *              2.token是否被篡改
 *              3.token是否过时
 * Author WEISANGENG
 * Date 2019/5/19
 **/
public class JwtFilter extends AccessControlFilter {


    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println("拦截到的url:" + httpServletRequest.getRequestURL().toString());
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(token)) {
            System.out.println("token is required");
			return false;
		}
        // 校验token是否有效
        //boolean verify = JwtUtil.verify(token, 1L, "f13d617138ef6a0d2b32579d524df73b");
        Claim claim = JwtUtil.getClaim(token, "userId");
        Claim claimUsername = JwtUtil.getClaim(token, "username");
        Claim claimPassword = JwtUtil.getClaim(token, "password");
        Long userId = null;
        String username = null;
        String password = null;
        if(claim != null){
            userId = claim.asLong();
        }
        if(claimUsername != null){
            username = claimUsername.asString();
        }
        if(claimPassword != null){
            password = claimPassword.asString();
        }
        //Long userId = StringUtils.isNotBlank(userIdStr) ? Long.parseLong(userIdStr) : null;

        JwtToken jwtToken = new JwtToken(token, userId,username,password);

        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }



}

