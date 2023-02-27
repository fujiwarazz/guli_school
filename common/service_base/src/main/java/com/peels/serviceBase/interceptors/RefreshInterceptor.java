package com.peels.serviceBase.interceptors;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.peels.commonUtils.HttpUtils.WebUtils;
import com.peels.commonUtils.Jwt.JwtUtil;
import com.peels.commonUtils.Result.Result;
import com.peels.serviceBase.systemConstants.EduConstant.SystemConstant;
import com.peels.serviceBase.systemConstants.RedisConstants.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class RefreshInterceptor implements HandlerInterceptor {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            boolean expireOrWrong = JwtUtil.checkToken(token);
            if (!expireOrWrong) {
                String key = RedisConstants.MEMBER_TOKEN + token;
                String memberId = (String) stringRedisTemplate.opsForHash().get(key, "memberId");
                String nickName = (String) stringRedisTemplate.opsForHash ().get(key, "nickName");
                if (StrUtil.isNotBlank(memberId) && StrUtil.isNotBlank(nickName)) {
                    //生成新token 返回
                    String newToken = JwtUtil.getJwtToken(memberId, nickName);
                    WebUtils.renderString(response,JSON.toJSONString(Result.Success(21110,SystemConstant.REFRESH_TOKEN,newToken)));
                    //设置新的redisTTL
                    stringRedisTemplate.expire(key, RedisConstants.MEMBER_TOKEN_TTL, TimeUnit.MILLISECONDS);
                    return true;
                } else {
                    //重新登录渲染
                    WebUtils.renderString(response, JSON.toJSONString(Result.Fail(20005,SystemConstant.NEED_LOGIN)));
                    return false;
                }
            }else{
                return true;
            }
        } else {
            return true;
        }
    }
}
