package com.june.service.back.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.log.LoggerDao;
import com.june.dto.log.LoggerDto;

/**
 * 
 * Logger信息Service <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月12日 下午5:42:59
 */
@Service
public class LoggerService extends BaseService<LoggerDao, LoggerDto> {

	/**
	 * Logger信息dao
	 */
	@Autowired
	protected LoggerDao loggerDao;

	/**
	 * 批量添加服务，供excel批量数据导入使用
	 * 
	 * @param list
	 * @date 2016年6月13日 下午2:35:33
	 * @writer wjw.happy.love@163.com
	 */
	public void addlist(java.util.List<LoggerDto> list) {
		loggerDao.addlist(list);
	}

	public void clear() {
		loggerDao.clear();
	}

}
