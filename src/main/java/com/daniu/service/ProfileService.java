package com.daniu.service;

import com.daniu.domain.entity.Profile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * ProfileService
 *
 * @author FangDaniu
 */
public interface ProfileService extends IService<Profile> {

    /**
     * 通过用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    Profile findByUserId(Long userId);

}
