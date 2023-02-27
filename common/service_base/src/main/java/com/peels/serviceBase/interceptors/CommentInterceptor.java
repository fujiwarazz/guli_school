package com.peels.serviceBase.interceptors;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.peels.commonUtils.HttpUtils.WebUtils;
import com.peels.commonUtils.Jwt.JwtUtil;
import com.peels.commonUtils.Result.Result;
import com.peels.commonUtils.ThreadUtil.ThreadUtil;
import com.peels.serviceBase.systemConstants.EduConstant.SystemConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            Result<?> fail = Result.Fail(SystemConstant.NEED_LOGIN);
            String result = JSON.toJSONString(fail);
            WebUtils.renderString(response,result);
            return false;
        }

        boolean res = JwtUtil.checkToken(token);
        if(!res){
            return false;
        }
        String memberId = JwtUtil.getMemberIdByJwtToken(request);

        System.out.println("-------------------------------------");
        System.out.println(memberId);
        ThreadUtil.set(memberId);
        System.out.println(ThreadUtil.getValue());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ThreadUtil.remove();

    }
}
