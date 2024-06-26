package com.daniu.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.daniu.common.auth.RoleType;
import com.daniu.common.auth.Roles;
import com.daniu.common.auth.SaTokenConfigure;
import com.daniu.common.exception.BusinessException;
import com.daniu.common.preview.Preview;
import com.daniu.common.response.ErrorCode;
import com.daniu.common.response.Page;
import com.daniu.common.response.R;
import com.daniu.domain.dto.UserDetailDto;
import com.daniu.domain.dto.UserPageDto;
import com.daniu.domain.request.AddUserRolesRequest;
import com.daniu.domain.request.RegisterUserRequest;
import com.daniu.domain.request.UpdatePasswordRequest;
import com.daniu.domain.request.UpdateProfileRequest;
import com.daniu.domain.request.UserPageRequest;
import com.daniu.service.UserService;
import cn.hutool.core.convert.NumberWithFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 *
 * @author FangDaniu
 * @since 2024/05/23
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户")
public class UserController {

    private final UserService userService;


    /**
     * 新建用户
     *
     * @param request 注册请求
     * @return {@link R }<{@link Void }>
     */
    @PostMapping
    @Roles({RoleType.SUPER_ADMIN})
    @Preview
    @Operation(summary = "新增用户")
    public R<Void> create(@RequestBody @Validated RegisterUserRequest request) {
        userService.register(request);
        return R.ok();
    }


    /**
     * 获取所有
     *
     * @param request 用户页请求
     * @return {@link R }<{@link Page }<{@link UserPageDto }>>
     */
    @GetMapping
    @Operation(summary = "获取所有")
    public R<Page<UserPageDto>> findAll(UserPageRequest request) {
        Page<UserPageDto> ret = userService.queryPage(request);
        return R.ok(ret);
    }


    /**
     * 根据id删除
     *
     * @param id id
     * @return {@link R }<{@link Void }>
     */
    @DeleteMapping("{id}")
    @Roles({RoleType.SUPER_ADMIN})
    @Preview
    @Operation(summary = "根据id删除")
    public R<Void> remove(@PathVariable Long id) {
        NumberWithFormat userIdFormat = (NumberWithFormat) StpUtil.getExtra(SaTokenConfigure.JWT_USER_ID_KEY);
        if (userIdFormat.longValue() == id) {
            throw new BusinessException(ErrorCode.ERR_11006, "非法操作，不能删除自己！");
        }
        userService.removeUser(id);
        return R.ok();
    }


    /**
     * 根据id更新
     *
     * @param id      id
     * @param request 用户角色请求
     * @return {@link R }<{@link Void }>
     */
    @PatchMapping("{id}")
    @Preview
    @Operation(summary = "根据id更新")
    public R<Void> update(@PathVariable Long id, @RequestBody AddUserRolesRequest request) {
        userService.addRoles(id, request);
        return R.ok();
    }


    /**
     * 更新资料
     *
     * @param id      id
     * @param request 更新用户信息请求
     * @return {@link R }<{@link Void }>
     */
    @PatchMapping("/profile/{id}")
    @Preview
    @Operation(summary = "更新资料")
    public R<Void> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileRequest request) {
        NumberWithFormat userIdFormat = (NumberWithFormat) StpUtil.getExtra(SaTokenConfigure.JWT_USER_ID_KEY);
        if (userIdFormat.longValue() != id) {
            throw new BusinessException(ErrorCode.ERR_11004, "越权操作，用户资料只能本人修改！");
        }
        userService.updateProfile(id, request);
        return R.ok();
    }


    /**
     * 用户信息
     * {@link UserService#detail(Long, String)}
     *
     * @return {@link R }<{@link UserDetailDto }>
     */
    @GetMapping("/detail")
    @Operation(summary = "用户信息")
    public R<UserDetailDto> detail() {
        NumberWithFormat userId =
                (NumberWithFormat) StpUtil.getExtra(SaTokenConfigure.JWT_USER_ID_KEY);
        String roleCode = (String) StpUtil.getExtra(SaTokenConfigure.JWT_CURRENT_ROLE_KEY);
        UserDetailDto detail = userService.detail(userId.longValue(), roleCode);
        return R.ok(detail);
    }


    /**
     * 根据用户名获取
     *
     * @param username 用户名
     * @return {@link R }<{@link Void }>
     */
    @GetMapping("/{username}")
    @Roles({RoleType.SUPER_ADMIN})
    @Operation(summary = "根据用户名获取")
    public R<Void> findByUsername(@PathVariable String username) {
        throw new BusinessException(ErrorCode.ERR_11006, "接口未实现");
    }


    /**
     * 查询用户的profile
     *
     * @param userId 用户id
     * @return {@link R }<{@link Void }>
     */
    @GetMapping("/profile/{userId}")
    @Operation(summary = "查询用户的profile")
    public R<Void> getUserProfile(@PathVariable Long userId) {
        throw new BusinessException(ErrorCode.ERR_11006, "接口未实现");
    }

    /**
     * 添加角色
     *
     * @param userId  用户id
     * @param request 用户角色请求
     * @return {@link R }<{@link Object }>
     */
    @PostMapping("/roles/add/{userId}")
    @Preview
    @Operation(summary = "添加角色")
    public R<Object> addRoles(@PathVariable Long userId, @RequestBody @Validated AddUserRolesRequest request) {
        userService.addRoles(userId, request);
        return R.ok();
    }

    /**
     * 重置密码
     *
     * @param userId  用户id
     * @param request 密码更新请求
     * @return {@link R }<{@link Object }>
     */
    @PatchMapping("password/reset/{userId}")
    @Roles({RoleType.SUPER_ADMIN})
    @Operation(summary = "重置密码")
    public R<Object> resetPassword(@PathVariable Long userId, @RequestBody @Validated UpdatePasswordRequest request) {
        userService.resetPassword(userId, request);
        return R.ok();
    }
}
