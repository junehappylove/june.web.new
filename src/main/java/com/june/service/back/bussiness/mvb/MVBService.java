package com.june.service.back.bussiness.mvb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.bussiness.mvb.MVBDao;
import com.june.dto.back.bussiness.mvb.MVBDto;

/**
 * 
 * MVB信息Service <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月12日 下午5:42:59
 */
@Service
public class MVBService extends BaseService<MVBDao,MVBDto> {

	/**
	 * MVB信息dao
	 */
	@Autowired
	protected MVBDao mvbDao;

	/**
	 * 批量添加服务，供excel批量数据导入使用
	 * @param list
	 * @date 2016年6月13日 下午2:35:33
	 * @writer wjw.happy.love@163.com
	 */
	public void addlist(java.util.List<MVBDto> list){
		mvbDao.addlist(list);
	}

}
