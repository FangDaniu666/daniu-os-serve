package com.daniu.service.impl;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.common.auth.SaTokenConfigure;
import com.daniu.common.exception.BusinessException;
import com.daniu.common.preview.PreviewProperties;
import com.daniu.common.response.ErrorCode;
import com.daniu.common.response.Page;
import com.daniu.domain.dto.*;
import com.daniu.domain.entity.Profile;
import com.daniu.domain.entity.Role;
import com.daniu.domain.entity.User;
import com.daniu.domain.entity.UserRole;
import com.daniu.domain.request.*;
import com.daniu.mapper.UserMapper;
import com.daniu.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * User Service impl
 *
 * @author FangDaniu
 * @since 2024/05/25
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RoleService roleService;

    private final ProfileService profileService;

    private final UserRoleService userRoleService;

    private final CaptchaService captchaService;

    private final PreviewProperties previewProperties;

    @Override
    public LoginTokenDto login(LoginRequest request) {
        User user = lambdaQuery().eq(User::getUsername, request.getUsername()).one();
        if (user == null) {
            throw new BusinessException(ErrorCode.ERR_10002);
        }
        // 预览环境下可快速登录，不用验证码
        if (Boolean.TRUE.equals(request.getIsQuick()) && Boolean.TRUE.equals(previewProperties.getPreview())) {
            return login(request, user);
        }
        if (StrUtil.isBlank(request.getCaptchaKey())
                || !captchaService.verify(request.getCaptchaKey(), request.getCaptcha())) {
            throw new BusinessException(ErrorCode.ERR_10003);
        }
        return login(request, user);
    }

    private LoginTokenDto login(LoginRequest request, User user) {
        boolean checkPw = BCrypt.checkpw(request.getPassword(), user.getPassword());
        if (checkPw) {
            // 查询用户的角色
            List<Role> roles = roleService.findRolesByUserId(user.getId());

            return generateToken(user, roles, roles.isEmpty() ? "" : roles.get(0).getCode());
        } else {
            throw new BusinessException(ErrorCode.ERR_10002);
        }
    }

    @Override
    public UserDetailDto detail(Long userId, String roleCode) {
        User user = getById(userId);
        UserDetailDto userDetailDto = user.convert(UserDetailDto.class);
        ProfileDto profileDto = profileService.findByUserId(userId).convert(ProfileDto.class);
        List<RoleDto> roleDtoList = roleService.findRolesByUserId(userId)
                .stream()
                .filter(Role::getEnable)
                .map(role -> role.convert(RoleDto.class))
                .toList();
        if (roleDtoList.isEmpty()) {
            throw new BusinessException(ErrorCode.ERR_11005);
        }
        userDetailDto.setProfile(profileDto);
        userDetailDto.setRoles(roleDtoList);
        for (RoleDto roleDto : roleDtoList) {
            if (roleDto.getCode().equals(roleCode)) {
                userDetailDto.setCurrentRole(roleDto);
                break;
            }
        }
        return userDetailDto;
    }

    @Override
    public LoginTokenDto switchRole(Long userId, String roleCode) {
        User user = getById(userId);
        List<Role> roles = roleService.findRolesByUserId(userId);
        Role currentRole = null;
        for (Role role : roles) {
            if (roleCode.equals(role.getCode())) {
                currentRole = role;
            }
        }
        if (currentRole == null) {
            throw new BusinessException(ErrorCode.ERR_11005);
        }
        return generateToken(user, roles, currentRole.getCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterUserRequest request) {
        boolean exists = lambdaQuery().eq(User::getUsername, request.getUsername()).exists();
        if (exists) {
            throw new BusinessException(ErrorCode.ERR_10001);
        }
        User user = request.convert(User.class);
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        this.save(user);

        Profile profile = Optional.ofNullable(request.getProfile()).orElse(new RegisterUserProfileRequest())
                .convert(Profile.class);
        profile.setUserId(user.getId());
        if (CollUtil.isNotEmpty(request.getRoleIds())) {
            List<UserRole> roleList = request.getRoleIds().stream()
                    .map(roleId -> {
                        UserRole userRole = new UserRole();
                        userRole.setUserId(user.getId());
                        userRole.setRoleId(roleId);
                        return userRole;
                    }).toList();
            userRoleService.saveBatch(roleList);
        }
        profileService.save(profile);
    }

    @Override
    public LoginTokenDto refreshToken() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        StpUtil.login(tokenInfo.getLoginId(), SaLoginConfig
                .setExtra(SaTokenConfigure.JWT_USER_ID_KEY,
                        StpUtil.getExtra(SaTokenConfigure.JWT_USER_ID_KEY))
                .setExtra(SaTokenConfigure.JWT_USERNAME_KEY,
                        StpUtil.getExtra(SaTokenConfigure.JWT_USERNAME_KEY))
                .setExtra(SaTokenConfigure.JWT_CURRENT_ROLE_KEY,
                        StpUtil.getExtra(SaTokenConfigure.JWT_CURRENT_ROLE_KEY))
                .setExtra(SaTokenConfigure.JWT_ROLE_LIST_KEY,
                        StpUtil.getExtra(SaTokenConfigure.JWT_ROLE_LIST_KEY))
        );
        SaTokenInfo newTokenInfo = StpUtil.getTokenInfo();
        LoginTokenDto dto = new LoginTokenDto();
        dto.setAccessToken(newTokenInfo.getTokenValue());
        return dto;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        String username = (String) StpUtil.getExtra(SaTokenConfigure.JWT_USERNAME_KEY);
        User user = lambdaQuery().select(User::getPassword).eq(User::getUsername, username).one();
        if (!BCrypt.checkpw(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.ERR_10004);
        }
        user.setPassword(BCrypt.hashpw(request.getNewPassword()));
        lambdaUpdate().set(User::getPassword, BCrypt.hashpw(request.getNewPassword()))
                .eq(User::getUsername, username)
                .update();
        StpUtil.logout();
    }

    @Override
    public Page<UserPageDto> queryPage(UserPageRequest request) {
        IPage<User> qp = request.toPage();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(request.getUsername()), User::getUsername, request.getUsername())
                .or()
                .eq(ObjectUtil.isNotNull(request.getEnable()), User::getEnable, request.getEnable());

        IPage<UserPageDto> ret = getBaseMapper().pageDetail(qp,
                        request.getUsername(),
                        request.getGender(),
                        request.getEnable())
                .convert(dto -> {
                    List<RoleDto> roleDtoList = roleService.findRolesByUserId(dto.getId()).stream()
                            .map(role -> role.convert(RoleDto.class)).toList();
                    dto.setRoles(roleDtoList);
                    return dto;
                });
        return Page.convert(ret);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(Long id) {
        if (id == 1) {
            throw new BusinessException(ErrorCode.ERR_11006, "不能删除根用户");
        }
        removeById(id);
        profileService.lambdaUpdate().eq(Profile::getUserId, id).remove();
    }

    @Override
    public void resetPassword(Long userId, UpdatePasswordRequest request) {
        String newPw = BCrypt.hashpw(request.getPassword());
        lambdaUpdate().eq(User::getId, userId)
                .set(User::getPassword, newPw)
                .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoles(Long userId, AddUserRolesRequest request) {
        userRoleService.lambdaUpdate().eq(UserRole::getUserId, userId).remove();
        List<UserRole> list = request.getRoleIds().stream()
                .map(roleId -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    return userRole;
                }).toList();
        userRoleService.saveBatch(list);
    }

    @Override
    public void updateProfile(Long id, UpdateProfileRequest request) {
        Profile profile = request.convert(Profile.class);
        profileService.updateById(profile);
    }


    private LoginTokenDto generateToken(User user, List<Role> roles, String currentRoleCode) {
        // 密码验证成功
        StpUtil.login(user.getId(),
                SaLoginConfig.setExtra(SaTokenConfigure.JWT_USER_ID_KEY, user.getId())
                        .setExtra(SaTokenConfigure.JWT_USERNAME_KEY, user.getUsername())
                        .setExtra(SaTokenConfigure.JWT_CURRENT_ROLE_KEY, currentRoleCode)
                        .setExtra(SaTokenConfigure.JWT_ROLE_LIST_KEY, roles));
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        LoginTokenDto dto = new LoginTokenDto();
        dto.setAccessToken(tokenInfo.getTokenValue());
        return dto;
    }

}
