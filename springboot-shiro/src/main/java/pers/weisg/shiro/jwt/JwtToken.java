package pers.weisg.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Description 这个类作用是什么
 * Author WEISANGENG
 * Date 2019/5/19
 **/
public class JwtToken implements AuthenticationToken {

    private String token;
    private String username;
    private Long userId;
    private String password;

    public JwtToken() {
    }

    public JwtToken(String token, Long userId, String username, String password) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

