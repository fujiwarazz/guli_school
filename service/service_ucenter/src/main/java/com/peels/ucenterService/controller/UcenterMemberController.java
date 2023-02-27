package com.peels.ucenterService.controller;


import com.peels.commonUtils.Jwt.JwtUtil;
import com.peels.commonUtils.Result.Result;
import com.peels.models.Ucenter.UcenterMember;
import com.peels.ucenterService.entity.dto.UserLoginDto;
import com.peels.ucenterService.service.IUcenterMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/eduCenter/member")
public class UcenterMemberController {
    @Resource
    IUcenterMemberService ucenterMemberService;

    @PostMapping("/logOut")
    public Result<?> logOut(@RequestBody String token){
        ucenterMemberService.logOut(token);
        return Result.Success();
    }
    //登录
    @PostMapping("/login")
    public Result<?> UserLogin(@RequestBody UserLoginDto userLoginDto) {
        String token = ucenterMemberService.login(userLoginDto);
        return Result.UseMap().addMap("token", token);
    }

    //注册
    @PostMapping("/register")
    public Result<?> UserReg(@RequestBody UserLoginDto userLoginDto) {
        ucenterMemberService.register(userLoginDto);
        return Result.Success();
    }

    @GetMapping("/getMemberInfo")
    public Result<?> getMemberInfo(HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(request);
        UcenterMember byId = ucenterMemberService.getById(memberIdByJwtToken);;
        return Result.UseMap().addMap("userInfo",byId);
    }

    @GetMapping("/getMemberInfo/{id}")
    public UcenterMember getMemberFullInfoById(@PathVariable String id) {
        return ucenterMemberService.getById(id);
    }

    @GetMapping("/getMemberInfoById/{id}")
    public Result<?> getMemberInfoById(@PathVariable String id){
        UcenterMember byId = ucenterMemberService.getById(id);
        String nickName = byId.getNickname();
        String avatar = byId.getAvatar();
        return Result.UseMap().addMap("nickname",nickName).addMap("avatar",avatar);
    }

    /**
     * 查询一天注册人数
     */
    @GetMapping("countRegisters/{day}")
    public Long getRegistersDaily(@PathVariable("day") String day){
        return ucenterMemberService.getRegisterDaily(day);
    }


}

