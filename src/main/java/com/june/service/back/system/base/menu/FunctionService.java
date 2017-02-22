/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.service.back.system.base.menu.FunctionService.java
 * 日期:2017年2月17日
 */
package com.june.service.back.system.base.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	FunctionDao fdao;
	
	/**
	 * 批量删除权限按钮 sys_function <br>
	 * sys_function的一项按钮被删除后同时需要关联删除表sys_role_function的相关数据<br>
	 * 这里是程序控制，不需要使用mysql数据库提供的级联操作
	 * @param list
	 * @date 2017年2月22日 下午6:16:28
	 * @writer junehappylove
	 */
	public void deleteList(List<FunctionDto> list){
		for (FunctionDto fdto : list) {
			super.deleteDto(fdto);
			fdao.deleteByBtnId(fdto.getBtn_id());//删除关联值
		}
	}
}
