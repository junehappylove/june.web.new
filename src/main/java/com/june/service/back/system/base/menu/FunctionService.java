/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.service.back.system.base.menu.FunctionService.java
 * 日期:2017年2月17日
 */
package com.june.service.back.system.base.menu;

import java.util.List;

import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.system.base.menu.FunctionDao;
import com.june.dto.back.system.base.FunctionDto;

/**
 * FunctionService <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年2月17日 下午5:46:37
 * @version 1.0.0
 */
@Service
public class FunctionService extends BaseService<FunctionDao, FunctionDto> {
	
	//批量删除
	public void deleteList(List<FunctionDto> list){
		for(FunctionDto fdto:list){
			super.deleteDto(fdto);
		}
	}
}
