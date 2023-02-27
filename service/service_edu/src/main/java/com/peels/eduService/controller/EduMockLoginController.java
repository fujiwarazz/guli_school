package com.peels.eduService.controller;

import com.peels.commonUtils.Result.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/user")
public class EduMockLoginController {

    @PostMapping("login")
    public Result<?> Login(){
        return Result.UseMap().addMap("token","admin");
    }

    @GetMapping("getInfo")
    public Result<?>getInfo(){
        return Result.UseMap().addMap("roles","[admin]").addMap("name","admin").addMap("avatar","https://cdn.acwing.com/media/user/profile/photo/154042_lg_5510bdf229.jpg");
    }

}
