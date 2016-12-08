package com.june.dao.front.portalinit;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.front.portalinit.ArticleDto;

/**  
* @Description: 门户首页初始化用dao
* @author	caiyang
* @date 2015年11月5日 上午9:36:43 
* @version V1.0  
 */
public interface PortalInitDao extends BaseDao<ArticleDto>{

	/**   
	 * @Description: 获取所有已审核通过的文章
	 * @author caiyang
	 * @param: @param articleDto
	 * @param: @return      
	 * @return: List<ArticleDto>      
	 * @throws   
	 */
	public List<ArticleDto> getArtical(ArticleDto articleDto);
}
