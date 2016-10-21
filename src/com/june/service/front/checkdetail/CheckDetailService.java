/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.service.front.checkdetail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.june.common.BaseService;
import com.june.dao.front.checkdetail.CheckDetailDao;
import com.june.dto.front.checkdetail.CommentDto;
import com.june.dto.front.portalinit.ArticleDto;

/**
 * @Description: 查看资讯详细信息用service
 * @author caiyang
 * @date 2015年11月5日 上午9:59:09
 * @version V1.0
 */
@Service
public class CheckDetailService extends BaseService<CheckDetailDao,CommentDto> {

	@Autowired
	protected CheckDetailDao checkDetailDao;

	/**
	 * @Description: 根据资讯id获取资讯
	 * @author caiyang
	 * @param: @return
	 * @return: ArticleDto
	 * @throws
	 */
	public ArticleDto getArticleById(ArticleDto articleDto) {

		return checkDetailDao.getArticleById(articleDto);
	}

	/**
	 * @Description: 获取评论
	 * @author caiyang
	 * @param: @param commentDto
	 * @param: @return
	 * @return: List<CommentDto>
	 * @throws
	 */
	public List<CommentDto> getComment(CommentDto commentDto) {
		return checkDetailDao.getComment(commentDto);
	}

	/**
	 * @Description: 将content表中的static更新成1，已生成html静态页面
	 * @author caiyang
	 * @param: @param articleDto
	 * @return: void
	 * @throws
	 */
	public void updateStatic(ArticleDto articleDto) {
		checkDetailDao.updateStatic(articleDto);
	}

	// 获取评论表的最新id
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int getLatestIdOfComment() {
		return checkDetailDao.getLatestIdOfComment();
	}

	// 将评论插入
	public void insertComment(CommentDto commentDto) {
		checkDetailDao.insertComment(commentDto);
	}

	// 获取评论
	public CommentDto commentList(CommentDto commentDto) {
		List<CommentDto> list = checkDetailDao.getPageList(commentDto);
		int total = checkDetailDao.getTotal(commentDto);
		commentDto.setRows(list);
		commentDto.setTotal(total);
		return commentDto;
	}
	
	//评论数量更新+1次
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateCommentcount(CommentDto commentDto)
	{
		checkDetailDao.updateCommentcount(commentDto);
	}
}
