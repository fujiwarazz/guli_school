package com.peels.Security.security;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.peels.commonUtils.HttpUtils.WebUtils;
import com.peels.commonUtils.Result.Result;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登出业务逻辑类
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;
    private StringRedisTemplate stringRedisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, StringRedisTemplate stringRedisTemplate) {
        this.tokenManager = tokenManager;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {
            tokenManager.removeToken(token);

            //清空当前用户缓存中的权限数据
            String userName = tokenManager.getUserFromToken(token);
            stringRedisTemplate.delete(userName);
        }
        WebUtils.renderString(response, JSON.toJSONString(Result.Success()));

    }

}