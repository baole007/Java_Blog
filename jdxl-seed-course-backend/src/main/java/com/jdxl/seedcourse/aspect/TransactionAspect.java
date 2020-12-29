package com.jdxl.seedcourse.aspect;

import io.shardingsphere.api.HintManager;
import io.shardingsphere.core.hint.HintManagerHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @author ynght
 * @date 2020-04-26
 */
@Slf4j
@Aspect
@Configuration
public class TransactionAspect {
    private ThreadLocal<HintManager> threadLocal = new ThreadLocal();

    public TransactionAspect() {
    }

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void executeApi() {
    }

    @Before("executeApi()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        try {
            HintManager hintManager = HintManagerHolder.get();
            if (hintManager == null) {
                hintManager = HintManager.getInstance();
                hintManager.setMasterRouteOnly();
            } else {
                if (!hintManager.isMasterRouteOnly()) {
                    hintManager.setMasterRouteOnly();
                }
            }
            this.threadLocal.set(hintManager);
        } catch (Exception ex) {
            log.error("设置主库路由发生异常 ex: " + ex, ex);
        }
    }

    @After("executeApi()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        try {
            HintManager hintManager = (HintManager) this.threadLocal.get();
            if (hintManager != null) {
                hintManager.close();
            }
        } catch (Exception ex) {
            log.error("关闭主库路由发生异常 ex: " + ex, ex);
        }
    }
}