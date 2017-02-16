/**
 * 
 */
package com.june.common;

import java.util.List;

import com.june.dto.back.common.TreeDto;

/**
 * 增删改查接口
 * 
 * @author 王俊伟
 * @date 2016年5月10日 下午12:21:09
 */
public interface BaseDao<T extends AbstractDTO> extends AbstractDao<T> {
	
	/**
	 * 添加一条记录
	 * 
	 * @param t 实体对象
	 */
	void add(T t);
	
	/**
	 * 批量添加
	 * @param list 实体列表
	 * @date 2016年6月28日 下午5:46:28
	 * @writer wjw.happy.love@163.com
	 */
	void addList(List<T> list);

	/**
	 * 删除一条记录
	 * 
	 * @param t 实体对象
	 */
	void delete(T t);

	/**
	 * 更新一条记录
	 * 
	 * @param t 实体对象
	 */
	void update(T t);
	
	/**
	 * 批量更新数据表
	 * @param list 实体列表
	 * @date 2016年12月15日 下午10:16:00
	 * @writer junehappylove
	 */
	void updateList(List<T> list);
	
	/**
	 * 获取一条记录
	 * 
	 * @param t 查询实体对象
	 * @return 实体对象
	 */
	T get(T t);

	/**
	 * 查询记录,根据指定条件查询，不带分页，一般情况下不采用此方法<br>
	 * 可以参考分页的查询方法
	 * @deprecated 参见分页或者非分页查询即可
	 * @param t 查询实体对象
	 */
	List<T> search(T t);
	
	/**
	 * 取树数据
	 * @param t 树TreeDto对象
	 * @return 树TreeDto列表
	 */
	List<TreeDto> getTree(TreeDto t);
}
