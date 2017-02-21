package com.june.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.june.common.annotation.DataSource;
import com.june.common.rwseperate.DynamicDataSourceHolder;

@Aspect
public class DataSourceAspect {

	@Pointcut("@annotation(com.june.common.annotation.DataSource)")
	public void update() {
	}
	
	@Before("update()")
    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSource(data.value());
                System.out.println(data.value());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
