/**
 * JUNE软件有限公司<br>
 * june.web.dto:com.june.dto.log.LoggerDto.java
 * 日期:2017年6月2日
 */
package com.june.dto.log;

import java.sql.Timestamp;

import com.june.common.PageDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LoggerDto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年6月2日 下午9:32:18
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LoggerDto extends PageDTO<LoggerDto>{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 20170602213452L;
	
	@Override
	protected String getDtoName() {
		return "LOGGER";//日志
	}

	private String loggerId;// 主键
	private String userId;// 操作用户
	private String operateType;// 操作类型
	private String operateModule; // 操作的功能模块
	private String operateRemark;// 操作注释
	private String operateMethod;// 操作的方法
	private String operateParams;// 参数
	private Timestamp operateTime;// 操作时间
	
}
