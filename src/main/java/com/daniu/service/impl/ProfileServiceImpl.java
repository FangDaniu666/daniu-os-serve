package com.daniu.service.impl;

import com.daniu.domain.entity.Profile;
import com.daniu.mapper.ProfileMapper;
import com.daniu.service.ProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * ProfileServiceImpl
 *
 * @author FangDaniu
 * @since 2024/05/25
 */
@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile>
    implements ProfileService {

    @Override
    public Profile findByUserId(Long userId) {
        return lambdaQuery().eq(Profile::getUserId, userId).one();
    }

}
