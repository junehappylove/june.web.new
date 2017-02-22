package com.june.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.june.dto.back.common.TreeDto;

/**
 * 
 * 基础服务类 <br>
 * 提供注解式事务支持
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月13日 上午11:42:15
 * @param <Dao>
 * @param <Dto>
 */
@Transactional
public abstract class BaseService<Dao extends BaseDao<Dto>, Dto extends PageDTO<Dto>> {

	protected static Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	@Autowired
	protected Dao dao;

	/**
	 * 分页查询相关Dto
	 * 
	 * @param dto
	 * @return Dto
	 * @date 2016年5月13日 下午1:50:03
	 * @writer wjw.happy.love@163.com
	 */
	public Dto getPagedDtos(Dto dto) {
		dto.setRows(dao.getPageList(dto));
		dto.setTotal(dao.getTotal(dto));
		return dto;
	}

	/**
	 * 获取相关条件下所有的Dto列表数据,不分页
	 * 
	 * @param dto 大多数情况下是传入null值
	 * @return List<Dto>
	 * @date 2016年5月13日 下午1:49:52
	 * @writer wjw.happy.love@163.com
	 */
	public List<Dto> getDtos(Dto dto) {
		return dao.getList(dto);
	}

	/**
	 * 不分页查询Dto
	 * @param dto
	 * @return
	 * @date 2016年6月28日 下午5:22:31
	 * @writer wjw.happy.love@163.com
	 */
	public Dto noPagedDtos(Dto dto){
		dto.setRows(this.getDtos(dto));
		dto.setTotal(dto.getRows().size());
		return dto;
	}
	
	/**
	 * 获取一条Dto,一搬是根据主键去查询唯一数据
	 * 
	 * @param dto
	 * @return Dto
	 * @date 2016年5月13日 下午1:49:43
	 * @writer wjw.happy.love@163.com
	 */
	public Dto getDtoById(Dto dto) {
		return dao.get(dto);
	}

	/**
	 * 添加一条Dto
	 * 
	 * @param dto
	 * @date 2016年5月13日 下午1:49:33
	 * @writer wjw.happy.love@163.com
	 */
	public void addDto(Dto dto) {
		dao.add(dto);
	}
	
	/**
	 * 批量添加
	 * @param list
	 * @date 2016年6月28日 下午5:45:39
	 * @writer wjw.happy.love@163.com
	 */
	public void addList(List<Dto> list){
		dao.addList(list);
	}

	/**
	 * 删除一条Dto<br>
	 * 也可以根据Dto条件删除.<br>
	 * 通常状况下是给定主键ID进行删除<br>
	 * 当然也可以自定义条件删除.
	 * @param dto
	 * @date 2016年5月13日 下午1:49:15
	 * @writer wjw.happy.love@163.com
	 */
	public void deleteDto(Dto dto) {
		dao.delete(dto);
	}
	
	/**
	 * 批量删除，只适合根据主键的删除(主键采用appid属性)
	 * @param dto
	 * @date 2017年2月16日 下午8:13:10
	 * @writer junehappylove
	 */
	public void deleteDtoByIds(Dto dto){
		String ids = dto.getIds();
		if (ids != null) {
			for (int i = 0; i < ids.split(",").length; i++) {
				dto.setAppid(ids.split(",")[i]);
				this.deleteDto(dto);
			}
		}
	}

	/**
	 * 更新一条Dto
	 * 也可以根据Dto条件更新.<br>
	 * 通常状况下是给定主键ID进行更新<br>
	 * 当然也可以自定义条件更新.
	 * 
	 * @param dto
	 * @date 2016年5月13日 下午1:48:53
	 * @writer wjw.happy.love@163.com
	 */
	public void updateDto(Dto dto) {
		dao.update(dto);
	}
	
	/**
	 * 批量更新操作
	 * @param list
	 * @date 2017年2月17日 下午7:31:34
	 * @writer junehappylove
	 */
	public void updateList(List<Dto> list){
		dao.updateList(list);
	}

	/**
	 * 取下拉数据
	 * 
	 * @param dto
	 * @return List<TreeDto>
	 * @date 2016年5月16日 上午10:54:48
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getDrops(Dto dto) {
		return dao.getDrops(dto);
	}
	
	/**
	 * 取树数据
	 * @param dto
	 * @return
	 * @date 2017年2月17日 下午7:31:50
	 * @writer junehappylove
	 */
	public List<TreeDto> getTree(TreeDto dto) {
		return dao.getTree(dto);
	}

	/**
	 * 设置检索的表，schema.table
	 * 
	 * @param abstractDTO
	 * @param schemaName
	 * @param tablesName
	 * @param params
	 * @date 2015年12月10日 下午5:44:23
	 */
	protected void setSchemaTable(AbstractDTO abstractDTO, String schemaName, String[] tablesName,
			Map<String, Object> params) {
		// 单表查询
		if (tablesName.length == 1 && abstractDTO != null) {
			// abstractDTO.setSchema_table(schemaName + "." + tablesName[0]);
		}
		// 多表查询
		else {
			for (int i = 0; i < tablesName.length; i++) {
				params.put(tablesName[i], schemaName + "." + tablesName[i]);
			}
		}
	}
	
	/**
	 * 判断是否ajax调用
	 * 
	 * @param request
	 * @return
	 * @date 2016年12月14日 下午11:04:51
	 * @writer junehappylove
	 */
	protected boolean isAjaxCall(HttpServletRequest request) {
		return ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
	}
}
