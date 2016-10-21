/**
 * 
 */
package com.june.common;

import java.util.List;

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
	 * @param t
	 */
	public void add(T t);
	
	/**
	 * 批量添加
	 * @param list
	 * @date 2016年6月28日 下午5:46:28
	 * @writer wjw.happy.love@163.com
	 */
	public void addList(List<T> list);

	/**
	 * 删除一条记录
	 * 
	 * @param t
	 */
	public void delete(T t);

	/**
	 * 更新一条记录
	 * 
	 * @param t
	 */
	public void update(T t);
	
	/**
	 * 获取一条记录
	 * 
	 * @param t
	 * @return
	 */
	public T get(T t);

	/**
	 * 查询记录,根据指定条件查询，不带分页，一般情况下不采用此方法<br>
	 * 可以参考分页的查询方法
	 * @deprecated
	 * @param t
	 */
	public List<T> search(T t);
}
