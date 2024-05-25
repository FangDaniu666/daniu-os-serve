package com.daniu.service.impl;

import com.daniu.domain.entity.UserRole;
import com.daniu.mapper.UserRoleMapper;
import com.daniu.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * UserRoleServiceImpl
 *
 * @author FangDaniu
 * @since 2024/05/25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {
}
