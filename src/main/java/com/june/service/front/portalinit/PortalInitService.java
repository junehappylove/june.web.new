package com.june.service.front.portalinit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.front.portalinit.PortalInitDao;
import com.june.dto.front.portalinit.ArticleDto;

/**  
* @Description: 门户首页初始化用service
* @author	caiyang
* @date 2015年11月5日 上午9:35:34 
* @version V1.0  
 */
@Service
public class PortalInitService extends BaseService<PortalInitDao, ArticleDto>{
	
	/**
	 * 门户首页初始化用dao注入
	 */
	@Autowired
	protected PortalInitDao portalInitDao;
	
	/**   
	 * @Description: 获取所有已经审批通过的文章
	 * @author	caiyang
	 * @param: @param articleDto
	 * @param: @return      
	 * @return: List<ArticleDto>      
	 * @throws   
	 */
	public List<ArticleDto> getArtical(ArticleDto articleDto)
	{
		List<ArticleDto> list = portalInitDao.getArtical(articleDto);
		return list;
	}
}
