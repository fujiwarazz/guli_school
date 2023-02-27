package com.peels.ucenterService.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.commonUtils.CommonUtils.DateUtil;
import com.peels.commonUtils.Encrypt.MD5;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.commonUtils.Jwt.JwtUtil;
import com.peels.models.Ucenter.UcenterMember;
import com.peels.serviceBase.systemConstants.RedisConstants.RedisConstants;
import com.peels.ucenterService.entity.dto.UserLoginDto;
import com.peels.ucenterService.mapper.UcenterMemberMapper;
import com.peels.ucenterService.service.IUcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-22
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements IUcenterMemberService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //TODO: 整合SpringSecurity + redis
    @Override
    public String login(UserLoginDto userLoginDto) {
        String phone = userLoginDto.getMobile();
        String password = userLoginDto.getPassword();
        if(StrUtil.isBlank(phone) || StrUtil.isBlank(password)){
            throw new BusinessException(20001,"账号密码不为空");
        }
        LambdaQueryWrapper<UcenterMember>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UcenterMember::getMobile,phone);
        UcenterMember loginUser = this.getOne(queryWrapper);
        if(loginUser==null){
            throw new BusinessException(20002,"登录失败");
        }
        if(MD5.encrypt(password).equals(userLoginDto.getPassword())){
            throw new BusinessException(20002,"登录失败");
        }

        if(loginUser.getIsDisabled()){
            throw new BusinessException(20002,"登录失败");
        }

        stringRedisTemplate.opsForValue().increment(RedisConstants.MEMBER_LOGIN_NUMBER+ DateUtil.getToday(),1L);

        return JwtUtil.getJwtToken(loginUser.getId(), loginUser.getNickname());
    }

    @Override
    public void register(UserLoginDto userLoginDto) {
        String code = userLoginDto.getCode();
        String phone = userLoginDto.getMobile();
        String password = userLoginDto.getPassword();
        if(StrUtil.isBlank(phone) || StrUtil.isBlank(code) || StrUtil.isBlank(password)){
            throw new BusinessException(20001,"缺少参数!");
        }

        String checkCode = stringRedisTemplate.opsForValue().get(RedisConstants.PHONE_CODE + phone);
        if(!code.equals(checkCode)){
            throw new BusinessException(20001,"验证码过期!");
        }
        Boolean NotExist =  checkPhoneEx(phone);
        if(!NotExist){
            throw new BusinessException(20010,"此账号已存在!");
        }
        UcenterMember ucenterMember = initMember(userLoginDto);
        this.save(ucenterMember);
    }

    //根据openid判断
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        LambdaQueryWrapper<UcenterMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UcenterMember::getOpenid,openid);
        return this.getOne(wrapper);
    }

    @Override
    public void logOut(String token) {
        String key = RedisConstants.MEMBER_TOKEN + token;
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long getRegisterDaily(String day) {
        return baseMapper.getRegisterDaily(day);
    }


    private UcenterMember initMember(UserLoginDto userLoginDto) {
        UcenterMember ucenterMember = new UcenterMember();
        if(userLoginDto.getNickname()!=null){
            ucenterMember.setNickname(userLoginDto.getNickname());
        }else{
            ucenterMember.setNickname("glxy"+RandomUtil.randomString(8));
        }
        ucenterMember.setPassword(MD5.encrypt(userLoginDto.getPassword()));
        ucenterMember.setMobile(userLoginDto.getMobile());
        ucenterMember.setAvatar("https://learning-glxy-images.oss-cn-hangzhou.aliyuncs.com/2022/10/21/67f390adbebf4688bc23e0e1d200979bfile.png");
        return ucenterMember;
    }

    private Boolean checkPhoneEx(String phone) {
        LambdaQueryWrapper<UcenterMember>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UcenterMember::getMobile,phone);
        UcenterMember one = this.getOne(queryWrapper);
        return one == null;
    }
}
