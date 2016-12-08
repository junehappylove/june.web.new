/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.dao.front.checkdetail;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.front.checkdetail.CommentDto;
import com.june.dto.front.portalinit.ArticleDto;

/**  
* @Description: 查看资讯详细用dao
* @author	caiyang
* @date 2015年11月5日 上午10:20:16 
* @version V1.0  
 */
public interface CheckDetailDao extends BaseDao<CommentDto>{
	
	/**   
	 * @Description: 根据资讯id获取资讯
	 * @author caiyang
	 * @param: @return      
	 * @return: ArticleDto      
	 * @throws   
	 */
	public ArticleDto getArticleById(ArticleDto articleDto);
	
	/**   
	 * @Description: 获取评论
	 * @author caiyang
	 * @param: @param commentDto
	 * @param: @return      
	 * @return: List<CommentDto>      
	 * @throws   
	 */
	public List<CommentDto> getComment(CommentDto commentDto);
	
	/**   
	 * @Description: 将content表中的static更新成1，已生成html静态页面
	 * @author caiyang
	 * @param: @param articleDto      
	 * @return: void      
	 * @throws   
	 */
	public void updateStatic(ArticleDto articleDto);
	
	/**   
	 * @Description: 获取评论id的sequence
	 * @author caiyang
	 * @param: @return      
	 * @return: int      
	 * @throws   
	 */
	public int getLatestIdOfComment();
	
	/**   
	 * @Description: 插入一条评论
	 * @author caiyang
	 * @param: @param commentDto      
	 * @return: void      
	 * @throws   
	 */
	public void insertComment(CommentDto commentDto);
	
	/**   
	 * @Description: 更新文章的评论次数，+1
	 * @author caiyang
	 * @param: @param articleDto      
	 * @return: void      
	 * @throws   
	 */
	public void updateCommentcount(CommentDto commentDto);
}
