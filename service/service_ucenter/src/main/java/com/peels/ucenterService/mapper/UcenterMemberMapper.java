package com.peels.ucenterService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peels.models.Ucenter.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-22
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Long getRegisterDaily(String day);
}
