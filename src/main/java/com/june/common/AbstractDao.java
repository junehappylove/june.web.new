package com.june.common;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.june.dto.back.common.TreeDto;

/**
 * 
 * AbstractDao <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月20日 下午9:23:38
 * @version 1.0.0
 * @param <T>
 */
public abstract interface AbstractDao<T extends AbstractDTO> {
	
	/**
	 * 获取一览数据
	 * 
	 * @param T
	 *            AbstractDTO
	 * @return List<T>
	 */
	@Cacheable(value = "getPageList", key = "#root.targetClass + #root.methodName + #root.args[0].currpage")
	List<T> getPageList(T t);

	/**
	 * 获取总条数
	 * 
	 * @param T
	 *            AbstractDTO
	 * @return int
	 */
	@Cacheable(value = "getTotal", key = "#root.targetClass + #root.methodName + #root.args[0].currpage")
	int getTotal(T t);

	/**
	 * 不分页获取相关条件下的所有列表数据
	 * 
	 * @param T
	 * @return
	 */
	List<T> getList(T t);

	/**
	 * 获取通用下拉数据
	 * TODO 后期还需要另重新设计下来数据，树形数据，列表数据等
	 * @param T
	 * @return 
	 */
	List<TreeDto> getDrops(T t);
}
