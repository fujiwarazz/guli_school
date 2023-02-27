package com.peels.ServiceAcl.controller;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.api.R;
import com.peels.ServiceAcl.service.IndexService;
import com.peels.commonUtils.Result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    @Resource
    private IndexService indexService;
    @PostMapping("info")
    public Result<?> info(@RequestParam String token){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object>  userInfo= indexService.getUserInfo(username);
        return Result.Success(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public Result<?> getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.UseMap().addMap("permissionList", permissionList);
    }

    @PostMapping("logout")
    public Result<?> logout(){
        return Result.Success();
    }

}
