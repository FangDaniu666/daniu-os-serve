package com.daniu.common.preview;

import com.daniu.common.exception.BusinessException;
import com.daniu.common.response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * {@link Preview}
 * 切面处理
 *
 * @author FangDaniu
 * @since  2024/05/22
 */
@Aspect
@Component
@ConditionalOnProperty(value = "dnos.preview", havingValue = "true")
@Slf4j
public class PreviewAspect implements InitializingBean {


    @Override
    public void afterPropertiesSet() {
        log.info("已打开预览环境，限制部分功能.");
    }


    @Pointcut("@annotation(Preview)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before() {
        throw new BusinessException(ErrorCode.ERR_30001);
    }


}
