package com.peels.ucenterService.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.models.Ucenter.UcenterMember;
import com.peels.ucenterService.entity.dto.UserLoginDto;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-22
 */
public interface IUcenterMemberService extends IService<UcenterMember> {

    String login(UserLoginDto userLoginDto);

    void register(UserLoginDto userLoginDto);

    UcenterMember getOpenIdMember(String openid);

    void logOut(String token);

    Long getRegisterDaily(String day);
}
