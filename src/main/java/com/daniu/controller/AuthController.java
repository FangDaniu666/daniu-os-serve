package com.daniu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.lang.Pair;
import com.daniu.common.auth.SaTokenConfigure;
import com.daniu.common.preview.Preview;
import com.daniu.common.response.R;
import com.daniu.domain.dto.LoginTokenDto;
import com.daniu.domain.request.ChangePasswordRequest;
import com.daniu.domain.request.LoginRequest;
import com.daniu.domain.request.RegisterUserRequest;
import com.daniu.service.CaptchaService;
import com.daniu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 鉴权相关的Controller.
 *
 * @author FangDaniu
 * @since 2024/05/22
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "鉴权")
public class AuthController {

    private final UserService userService;

    private final CaptchaService captchaService;

    private static final String CAPTCHA_KEY = "captchaKey";

    /**
     * 用户登录接口.
     *
     * @param request            登录请求
     * @param httpServletRequest http servlet请求
     * @return 返回token
     */
    @PostMapping("/login")
    @Operation(summary = "登录")
    public R<LoginTokenDto> login(@RequestBody final LoginRequest request,
                                  HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String captchaKey = (String) session.getAttribute(CAPTCHA_KEY);
        if (captchaKey != null) {
            request.setCaptchaKey(captchaKey);
        }
        LoginTokenDto tokenDto = userService.login(request);
        return R.ok(tokenDto);
    }

    /**
     * 注册
     *
     * @param request 请求
     * @return {@link R }<{@link Void }>
     */
    @PostMapping("/register")
    @Preview
    @Operation(summary = "注册")
    public R<Void> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return R.ok();
    }

    /**
     * 刷新token
     *
     * @return 新的token
     */
    @GetMapping("/refresh/token")
    @Operation(summary = "刷新token")
    public R<LoginTokenDto> refreshToken() {
        LoginTokenDto dto = userService.refreshToken();
        return R.ok(dto);
    }


    /**
     * 切换角色
     *
     * @param roleCode 角色代码
     * @return R
     */
    @PostMapping("/current-role/switch/{roleCode}")
    @Operation(summary = "切换用户")
    public R<LoginTokenDto> switchRole(@PathVariable String roleCode) {
        NumberWithFormat userId =
                (NumberWithFormat) StpUtil.getExtra(SaTokenConfigure.JWT_USER_ID_KEY);
        LoginTokenDto tokenDto = userService.switchRole(userId.longValue(), roleCode);
        return R.ok(tokenDto);
    }

    /**
     * 登出，当使用无状态的jwt时，注销登录不需要任何操作
     *
     * @return R
     */
    @PostMapping("/logout")
    @Operation(summary = "登出")
    public R<Object> logout() {
        StpUtil.logout();
        return R.ok();
    }

    /**
     * 图形验证码
     *
     * @param request  http servlet请求
     * @param response 响应
     * @throws IOException IOException
     */
    @GetMapping("/captcha")
    @Operation(summary = "验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Pair<String, ICaptcha> captchaPair = captchaService.create();
        HttpSession session = request.getSession();
        session.setAttribute(CAPTCHA_KEY, captchaPair.getKey());
        captchaPair.getValue().write(response.getOutputStream());
    }

    /**
     * 修改密码
     *
     * @param request 请求
     * @return R
     */
    @PostMapping("/password")
    @Preview
    @Operation(summary = "修改密码")
    public R<Object> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return R.ok();
    }

}
