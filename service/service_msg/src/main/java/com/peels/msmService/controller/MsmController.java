package com.peels.msmService.controller;

import com.peels.commonUtils.Result.Result;
import com.peels.msmService.service.SmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Resource
    SmsService smsService;
    @GetMapping("/send/{phone}")
    public Result<?> sendMsm(@PathVariable String phone){
        smsService.sendMsm(phone);
        return Result.Success();
    }

}
