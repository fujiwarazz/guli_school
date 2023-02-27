package com.peels.ServiceAcl.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.ServiceAcl.entity.UserRole;
import com.peels.ServiceAcl.mapper.UserRoleMapper;
import com.peels.ServiceAcl.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
