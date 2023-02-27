package com.peels.msmService.service.Impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.commonUtils.Msm.SendMsmUtil;
import com.peels.msmService.service.SmsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public void sendMsm(String phone) {
        String phonePrefix = "phoneCode:";
        String s = stringRedisTemplate.opsForValue().get(phonePrefix + phone);
        if(StrUtil.isNotBlank(s)){
            return;
        }
        String code;
        try {
            code = RandomUtil.randomNumbers(4);
            SendMsmUtil.Send(phone,code);
        } catch (Exception e) {
            throw new BusinessException(20001,"验证码发送失败");
        }
        stringRedisTemplate.opsForValue().set("phoneCode:"+phone,code,5L,TimeUnit.MINUTES);
    }
}
