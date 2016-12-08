package com.june.common;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.june.dto.back.common.LogOperateDto;
import com.june.service.back.common.LogOperateService;

/**
 * @Description 操作日志记录，添加、删除、修改、查询等方法的AOP
 * @author liren
 * @Date 2015年12月10日
 */
@Aspect
public class LogAspect {

	/**
	 * 日志记录 service
	 */
	@Autowired
	private LogOperateService logService;

	@Pointcut("@annotation(com.june.common.MethodLog)")
	public void methodCachePointcut() {
	}

	@SuppressWarnings("static-access")
	@Around("methodCachePointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		// 获取登录管理员id
		String adminUserId = logService.getLoginUserId();
		if (adminUserId == null) {// 没有管理员登录
			return null;
		}

		String className = point.getThis().getClass().getName(); // 类名
		if (className.indexOf("$$EnhancerByCGLIB$$") > -1) { // 如果是CGLIB动态生成的类
			try {
				className = className.substring(0, className.indexOf("$$"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		String methodName = className + "." + point.getSignature().getName(); // 获取方法名

		Object[] methodParam = null; // 参数
		Object object;
		String params = "";
		try {
			methodParam = point.getArgs(); // 获取方法参数

			// 判断参数类型
			if (methodParam[0] instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> paramsMap = logService.getParameterMap((Map<String, Object>) methodParam[0]);
				params = logService.mapToString(paramsMap);
			} else if (methodParam[0] instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) methodParam[0];

				String appParams = request.getParameter("paramsJson"); // app端传来的参数

				if (!StringUtils.isBlank(appParams)
						&& !StringUtils.isEmpty(appParams)) {
					//如果是APP端操作，修改编码
					String udStr = URLDecoder.decode(appParams, "UTF-8");
					params = udStr;
				} else {
					params = logService.getParams(request);
				}
			}

			object = point.proceed();
		} catch (Exception e) {
			// 异常处理记录日志..log.error(e);
			throw e;
		}

		Map<String, String> methodMap = getMethodRemark(point);

		String methodRemark = ""; // 操作备注
		String operateType = ""; // 操作类型
		String funModule = ""; // 功能模块
		if (methodMap != null) {
			funModule = methodMap.get("module");
			methodRemark = methodMap.get("remark"); // 获取操作备注
			operateType = methodMap.get("operateType"); // 获取操作类型
		}

		String operateRemark = methodRemark; // 操作备注

		LogOperateDto logDto = new LogOperateDto();
		logDto.setUserId(adminUserId);
		logDto.setFunModule(funModule);
		logDto.setOperateType(operateType);
		logDto.setOperateRemark(operateRemark);
		logDto.setOperateMethod(methodName);
		logDto.setOpetateParams(params);
		//String time = DateUtil.getTimeOfNow("yyyy-MM-dd HH:mm:ss");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		logDto.setOperateTime(now);

		logService.addLogOperate(logDto); // 添加日志记录

		return object;
	}

	/**
	 * @Description 获取方法的中文备注，用于记录用户的操作日志描述
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMethodRemark(JoinPoint joinPoint)
			throws Exception {
		Map<String, String> methodMap = new HashMap<String, String>();

		Class<?> targetClass = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		Object[] parameterTypes = joinPoint.getArgs();

		for (Method method : targetClass.getDeclaredMethods()) {
			if (method.getName().equals(methodName)
					&& method.getParameterTypes().length == parameterTypes.length) {
				MethodLog methodLog = method.getAnnotation(MethodLog.class);
				if (methodLog != null) {
					methodMap.put("module", methodLog.module());
					methodMap.put("remark", methodLog.remark());
					methodMap.put("operateType", methodLog.operateType());
					return methodMap;
				}
				break;
			}
		}

		return null;
	}
}
