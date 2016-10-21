package com.june.dao.back.bussiness.mvb;

import com.june.common.BaseDao;
import com.june.dto.back.bussiness.mvb.MVBDto;

/**
 * 
 * MVBDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月17日 上午11:11:25
 */
public interface MVBDao extends BaseDao<MVBDto> {
	
	/**
	 * 批量添加MVB数据
	 * @param list
	 * @date 2016年6月17日 上午11:12:00
	 * @writer wjw.happy.love@163.com
	 */
	void addlist(java.util.List<MVBDto> list);

}
