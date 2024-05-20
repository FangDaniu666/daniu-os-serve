package com.daniu.pms.service.impl;

import com.daniu.pms.domain.entity.UserRole;
import com.daniu.pms.mapper.UserRoleMapper;
import com.daniu.pms.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * UserRoleServiceImpl
 *
 * @author FangDaniu
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {
}
