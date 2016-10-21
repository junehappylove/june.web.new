package com.june.common;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.june.dto.back.common.TreeDto;

/**
 * 
 * 基础服务类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月13日 上午11:42:15
 * @param <DAO>
 * @param <DTO>
 */
@Transactional
public abstract class BaseService<DAO extends BaseDao<DTO>, DTO extends PageDTO<DTO>> {

	protected static Logger logger = LoggerFactory.getLogger(BaseService.class);
	@Autowired
	protected DAO dao;

	/**
	 * 分页查询相关DTO
	 * 
	 * @param dto
	 * @return DTO
	 * @date 2016年5月13日 下午1:50:03
	 * @writer wjw.happy.love@163.com
	 */
	public DTO getPagedDtos(DTO dto) {
		dto.setRows(dao.getPageList(dto));
		dto.setTotal(dao.getTotal(dto));
		return dto;
	}

	/**
	 * 获取相关条件下所有的DTO列表数据,不分页
	 * 
	 * @param dto 大多数情况下是传入null值
	 * @return List<DTO>
	 * @date 2016年5月13日 下午1:49:52
	 * @writer wjw.happy.love@163.com
	 */
	public List<DTO> getDtos(DTO dto) {
		return dao.getList(dto);
	}

	/**
	 * 不分页查询DTO
	 * @param dto
	 * @return
	 * @date 2016年6月28日 下午5:22:31
	 * @writer wjw.happy.love@163.com
	 */
	public DTO noPagedDtos(DTO dto){
		dto.setRows(this.getDtos(dto));
		dto.setTotal(dto.getRows().size());
		return dto;
	}
	
	/**
	 * 获取一条DTO
	 * 
	 * @param dto
	 * @return DTO
	 * @date 2016年5月13日 下午1:49:43
	 * @writer wjw.happy.love@163.com
	 */
	public DTO getDtoById(DTO dto) {
		return dao.get(dto);
	}

	/**
	 * 添加一条DTO
	 * 
	 * @param dto
	 * @date 2016年5月13日 下午1:49:33
	 * @writer wjw.happy.love@163.com
	 */
	public void addDto(DTO dto) {
		dao.add(dto);
	}
	
	/**
	 * 批量添加
	 * @param list
	 * @date 2016年6月28日 下午5:45:39
	 * @writer wjw.happy.love@163.com
	 */
	public void addList(List<DTO> list){
		dao.addList(list);
	}

	/**
	 * 删除一条DTO<br>
	 * 也可以根据DTO条件删除.<br>
	 * 通常状况下是给定主键ID进行删除<br>
	 * 当然也可以自定义条件删除.
	 * @param dto
	 * @date 2016年5月13日 下午1:49:15
	 * @writer wjw.happy.love@163.com
	 */
	public void deleteDtoById(DTO dto) {
		dao.delete(dto);
	}

	/**
	 * 更新一条DTO
	 * 
	 * @param dto
	 * @date 2016年5月13日 下午1:48:53
	 * @writer wjw.happy.love@163.com
	 */
	public void updateDtoById(DTO dto) {
		dao.update(dto);
	}

	/**
	 * 取下拉数据
	 * 
	 * @param dto
	 * @return List<TreeDto>
	 * @date 2016年5月16日 上午10:54:48
	 * @writer wjw.happy.love@163.com
	 */
	public List<TreeDto> getDrops(DTO dto) {
		return dao.getDrops(dto);
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
}
