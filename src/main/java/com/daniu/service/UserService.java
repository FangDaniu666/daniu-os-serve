package com.daniu.service;

import com.daniu.common.response.Page;
import com.daniu.domain.dto.LoginTokenDto;
import com.daniu.domain.dto.UserDetailDto;
import com.daniu.domain.dto.UserPageDto;
import com.daniu.domain.entity.User;
import com.daniu.domain.request.AddUserRolesRequest;
import com.daniu.domain.request.ChangePasswordRequest;
import com.daniu.domain.request.LoginRequest;
import com.daniu.domain.request.RegisterUserRequest;
import com.daniu.domain.request.UpdatePasswordRequest;
import com.daniu.domain.request.UpdateProfileRequest;
import com.daniu.domain.request.UserPageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * User Service
 *
 * @author FangDaniu
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param request 请求
     * @return 返回token
     */
    LoginTokenDto login(LoginRequest request);

    /**
     * 用户信息
     *
     * @param userId   用户id
     * @param roleCode 用户角色编码
     * @return 用户信息
     */
    UserDetailDto detail(Long userId, String roleCode);

    /**
     * 切换角色
     *
     * @param userId   用户id
     * @param roleCode 角色编码
     * @return 切换后重新获取token
     */
    LoginTokenDto switchRole(Long userId, String roleCode);

    /**
     * 注册用户
     *
     * @param request 请求
     */
    void register(RegisterUserRequest request);

    /**
     * 刷新token
     */
    LoginTokenDto refreshToken();

    /**
     * 修改密码
     *
     * @param request 请求
     */
    void changePassword(ChangePasswordRequest request);

    /**
     * 分页查询
     *
     * @param request 请求
     * @return ret
     */
    Page<UserPageDto> queryPage(UserPageRequest request);

    /**
     * 根据id删除用户，不能删除自己
     *
     * @param id 用户id
     */
    void removeUser(Long id);

    /**
     * 重置密码
     *
     * @param userId  用户id
     * @param request 包含密码
     */
    void resetPassword(Long userId, UpdatePasswordRequest request);

    /**
     * 给用户分配角色
     *
     * @param userId  用户id
     * @param request 包含角色id
     */
    void addRoles(Long userId, AddUserRolesRequest request);

    /**
     * 更新用户信息
     *
     * @param id      用户id
     * @param request 用户信息
     */
    void updateProfile(Long id, UpdateProfileRequest request);
}
