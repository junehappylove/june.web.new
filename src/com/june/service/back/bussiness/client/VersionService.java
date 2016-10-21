package com.june.service.back.bussiness.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.bussiness.client.VersionDao;
import com.june.dto.back.bussiness.client.VersionDto;

/**
 * 
 * 客户端版本信息Service <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月12日 下午5:42:59
 */
@Service
public class VersionService extends BaseService<VersionDao,VersionDto> {

	/**
	 * 客户端版本信息dao
	 */
	@Autowired
	protected VersionDao versionDao;

}
