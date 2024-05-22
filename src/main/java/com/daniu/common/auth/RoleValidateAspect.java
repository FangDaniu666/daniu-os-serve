package com.daniu.common.auth;

import cn.dev33.satoken.stp.StpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 角色检查切面
 *
 * @author FangDaniu
 * @since  2024/05/22
 */
@Aspect
@Component
public class RoleValidateAspect {


    /**
     * 切点
     */
    @Pointcut("@annotation(Roles)")
    public void pointcut() {

    }


    /**
     * 在执行方法前处理
     *
     * @param joinPoint 切点
     */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature methodSignature) {
            Roles roles = methodSignature.getMethod().getAnnotation(Roles.class);
            StpUtil.checkRoleOr(roles.value());
        }
    }

}
