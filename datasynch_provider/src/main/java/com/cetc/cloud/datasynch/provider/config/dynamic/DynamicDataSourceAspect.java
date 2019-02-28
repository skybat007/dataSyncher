package com.cetc.cloud.datasynch.provider.config.dynamic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *  *切换数据源Advice
 *  *@author Angel(QQ:412887952)
 *  *@version v.0.1
 *  
 */

@Aspect
@Order(-1)//保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

    /**
     *  @Before：在方法执行之前进行执行：
     *  @annotation(targetDataSource)：
     * 会拦截注解targetDataSource的方法，否则不拦截;
     */
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {

        //获取当前的指定的数据源;

        String dsId = targetDataSource.name();

        //如果不在我们注入的所有的数据源范围之内，那么输出警告信息，系统自动使用默认的数据源。

        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {

            logger.error("数据源[{}]不存在，使用默认数据源 > {}" + targetDataSource.name() + point.getSignature());

        } else {

            System.out.println("UseDataSource : {} > {}" + targetDataSource.name() + point.getSignature());

            //找到的话，那么设置到动态数据源上下文中。
            DynamicDataSourceContextHolder.setDataSourceType(targetDataSource.name());
        }

    }


    @After("@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {

        System.out.println("RevertDataSource : {} > {}" + targetDataSource.name() + point.getSignature());
        //方法执行完毕之后，销毁当前数据源信息，进行垃圾回收。
        DynamicDataSourceContextHolder.clearDataSourceType();

    }


}