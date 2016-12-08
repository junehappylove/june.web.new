package com.june.service.back.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.june.common.BaseController;
import com.june.common.BaseService;
import com.june.dao.back.common.LogOperateDao;
import com.june.dao.back.systemset.basicset.userinfo.UserInfoDao;
import com.june.dto.back.common.LogOperateDto;
import com.june.dto.back.systemset.basicset.UserInfoDto;

/**
 * 操作日志Service
 * 
 * @author liren
 * @Date 2015年12月10日
 */
@Service
public class LogOperateService extends BaseService<LogOperateDao, LogOperateDto> {

	@Autowired
	private LogOperateDao logOperateDao;

	@Autowired
	private UserInfoDao userInfoDao;

	/**
	 * 添加日志
	 * 
	 * @param logOperateDto
	 */
	public void addLogOperate(LogOperateDto logOperateDto) {
		logOperateDao.addLogOperate(logOperateDto);
	}

	/**
	 * 获取登录用户id
	 * 
	 * @return
	 */
	public String getLoginUserId() {

		// 获取登录管理员帐号名
		String userId = (String) SecurityUtils.getSubject().getPrincipal();

		if (userId == null || userId.equals("")) {
			return null;
		}
		// 判断该用户是否存在
		UserInfoDto userInfoDto = userInfoDao.get(userId);

		if (userInfoDto == null) {
			return null;
		}
		return userId;
	}

	/**
	 * 获取参数
	 * 
	 * @param httpServletRequest
	 */
	@SuppressWarnings("rawtypes")
	public String getParams(HttpServletRequest httpServletRequest) {
		Map paramsMap = new HashMap();
		boolean isAjaxCall = (new BaseController()).isAjaxCall(httpServletRequest);
		if (isAjaxCall) {
			paramsMap = WebUtils.getParametersStartingWith(httpServletRequest, "");
			paramsMap = getParameterMap(paramsMap);
		} else {
			paramsMap = getParameterMap((httpServletRequest.getParameterMap()));
		}
		@SuppressWarnings("unchecked")
		String params = mapToString(paramsMap);
		return params;
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getParameterMap(Map map) {
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<?> entries = map.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}

			if (valueObj != null && !StringUtils.isBlank(value) && !name.equals("_") && name != "_") {
				returnMap.put(name, value);
			}
		}
		return returnMap;
	}

	/**
	 * 将map转为String
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToString(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(map.keySet());

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key).toString();
			sb.append(key + "=" + value);
			sb.append(",");
		}
		String s = sb.toString();
		if (s.endsWith(",")) {
			s = s.substring(0, s.lastIndexOf(","));
		}
		return s;
	}
}
