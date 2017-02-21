package com.june.controller.front.checkdetail;

/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.june.common.BaseController;
import com.june.dto.front.checkdetail.CommentDto;
import com.june.dto.front.portalinit.ArticleDto;
import com.june.service.front.checkdetail.CheckDetailService;
import com.june.util.FreemarkerUtil;
import com.june.util.exception.CustomException;

import freemarker.template.TemplateException;

/**
 * @Description: 查看资讯详细信息用controller
 * @author caiyang
 * @date 2015年11月5日 上午9:59:09
 * @version V1.0
 */
@Controller
@RequestMapping("/checkDetail")
public class CheckDetailController extends BaseController<ArticleDto> {

	@Autowired
	protected CheckDetailService checkDetailService;

	@RequestMapping("/detail/{id}")
	public String checkDetail(@PathVariable(value = "id") Integer id,
			HttpServletRequest request,
			HttpServletResponse response) throws TemplateException {
		ArticleDto articleDto = new ArticleDto();
		articleDto.setContentId(Integer.toString(id));
		// 先判断是否有此静态页面，没有的话先生存，有的话直接进入到此页面中
		articleDto = checkDetailService.getArticleById(articleDto);
		if (articleDto == null) {
			throw new CustomException("该文章不存在！");
		} else {
			if (articleDto.getStaticPage() == null
					|| articleDto.getStaticPage().equals("0")) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 将新闻的内容放入到map中
				map.put("entity", articleDto);
				// 是否允许评论
				if (articleDto.getCommentState().equals("0")) {
					// 取得评论内容，并将其放入到map中
					CommentDto commentDto = new CommentDto();
					commentDto.setCurrpage(1);
					commentDto.setPageSize(5);
					// commentDto.setCommentId(id.toString());
					commentDto.setContentId(Integer.toString(id));
					List<CommentDto> list = new ArrayList<CommentDto>();
					list = checkDetailService.getComment(commentDto);
					map.put("list", list);
				}
				// 将db中portal_content表中的static字段更新成1（已经生成静态页）
				 checkDetailService.updateStatic(articleDto);
				FreemarkerUtil.creteHtml(article_template_path,
						articleDto.getCheckTime().substring(0, 10) + "-"
								+ articleDto.getContentId() + ".html", map,
						articleDto.getChannelPath() + "/"
								+ articleDto.getCheckTime().substring(0, 10)
								+ "/", request, response);
			}
		}
		String ctp = (String) (request.getContextPath());
		request.setAttribute("url",
				ctp + "/" + articleDto.getChannelPath() + "/"
						+ articleDto.getCheckTime().substring(0, 10) + "/"
						+ articleDto.getCheckTime().substring(0, 10) + "-"
						+ articleDto.getContentId() + ".html");
		return "/util/urlRedirect";
	}

	// 查看更多评论
	@RequestMapping("/morecomment/{id}")
	public String moreComments(@PathVariable(value = "id") Integer id,
			HttpServletRequest request,
			HttpServletResponse response) {
		CommentDto commentDto = new CommentDto();
		// 获取要跳转的页
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null) {
			pageNo = "1";
		}
		commentDto.setCurrpage(Integer.parseInt(pageNo));
		commentDto.setPageSize(15);
		commentDto.setContentId(Integer.toString(id));
		commentDto = checkDetailService.commentList(commentDto);
		ArticleDto articleDto = new ArticleDto();
		articleDto.setContentId(Integer.toString(id));
		articleDto = checkDetailService.getArticleById(articleDto);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", articleDto);
		map.put("list", commentDto.getRows());
		map.put("total", commentDto.getTotal());
		map.put("totalPage",
				(commentDto.getTotal() / commentDto.getPageSize()) + 1);
		map.put("pagesize", commentDto.getPageSize());
		map.put("currentPage", commentDto.getCurrpage());
		map.put("prePage", commentDto.getCurrpage() - 1);
		map.put("nextPage", commentDto.getCurrpage() + 1);
		map.put("id", id);
		String htmlString = FreemarkerUtil.printString(
				commentlist_template_path, map, request);
		request.setAttribute("url", htmlString);
		return "/util/urlRedirect";
	}

	/**
	 * @Description: 发表评论
	 * @author caiyang
	 * @param: @param request
	 * @param: @param response
	 * @param: @return
	 * @param: @throws TemplateException
	 * @param: @throws UnsupportedEncodingException
	 * @return: ModelAndView
	 * @throws
	 */
	@RequestMapping("/comment")
	public ModelAndView articleComment(HttpServletRequest request,
			HttpServletResponse response) throws TemplateException,
			UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		// 获取文章的id
		String id = (String) request.getParameter("id");
		// 获取评论内容
		String content = (String) request.getParameter("text");
		// 获取t_comment表的最新id
		int commentId = checkDetailService.getLatestIdOfComment();
		CommentDto commentDto = new CommentDto();
		commentDto.setContentId(id);
		commentDto.setCommentContent(content);
		commentDto.setCommentId(Integer.toString(commentId));
		commentDto.setCommentUser("caiyang");
		checkDetailService.insertComment(commentDto);
		//更新评论数量
		checkDetailService.updateCommentcount(commentDto);
		ArticleDto articleDto = new ArticleDto();
		articleDto.setContentId(id);
		articleDto = checkDetailService.getArticleById(articleDto);
		Map<String, Object> map = new HashMap<String, Object>();
		// 将新闻的内容放入到map中
		map.put("entity", articleDto);
		// 取得评论内容，并将其放入到map中
		commentDto.setCurrpage(1);
		commentDto.setPageSize(5);
		List<CommentDto> list = new ArrayList<CommentDto>();
		list = checkDetailService.getComment(commentDto);
		map.put("list", list);
		FreemarkerUtil.creteHtml(
				article_template_path,
				articleDto.getCheckTime().substring(0, 10) + "-"
						+ articleDto.getContentId() + ".html", map,
				articleDto.getChannelPath() + "/"
						+ articleDto.getCheckTime().substring(0, 10) + "/",
				request, response);
		String ctp = (String) (request.getContextPath());
		request.setAttribute("url",
				ctp + "/" + articleDto.getChannelPath() + "/"
						+ articleDto.getCheckTime().substring(0, 10) + "/"
						+ articleDto.getCheckTime().substring(0, 10) + "-"
						+ articleDto.getContentId() + ".html");
		ModelAndView result = new ModelAndView("/util/urlRedirect");
		return result;
	}
	
	@RequestMapping("/commentInMoreComment")
	public String commentInMoreComment(HttpServletRequest request,
			HttpServletResponse response) throws TemplateException, UnsupportedEncodingException
	{
		request.setCharacterEncoding("UTF-8");
		// 获取文章的id
		String id = (String) request.getParameter("id");
		// 获取评论内容
		String content = (String) request.getParameter("text");
		// 获取t_comment表的最新id
		int commentId = checkDetailService.getLatestIdOfComment();
		CommentDto commentDto = new CommentDto();
		commentDto.setContentId(id);
		commentDto.setCommentContent(content);
		commentDto.setCommentId(Integer.toString(commentId));
		commentDto.setCommentUser("caiyang");
		//插入评论
		checkDetailService.insertComment(commentDto);
		//更新评论数量
		checkDetailService.updateCommentcount(commentDto);
		ArticleDto articleDto = new ArticleDto();
		articleDto.setContentId(id);
		articleDto = checkDetailService.getArticleById(articleDto);
		// 获取要跳转的页
		/*String pageNo = request.getParameter("pageNo");
		if (pageNo == null) {
			pageNo = "1";
		}*/
		commentDto.setCurrpage(1);
		commentDto.setPageSize(15);
		commentDto.setContentId(id);
		commentDto = checkDetailService.commentList(commentDto);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", articleDto);
		map.put("list", commentDto.getRows());
		map.put("total", commentDto.getTotal());
		map.put("totalPage",
				(commentDto.getTotal() / commentDto.getPageSize()) + 1);
		map.put("pagesize", commentDto.getPageSize());
		map.put("currentPage", commentDto.getCurrpage());
		map.put("prePage", commentDto.getCurrpage() - 1);
		map.put("nextPage", commentDto.getCurrpage() + 1);
		map.put("id", id);
		String htmlString = FreemarkerUtil.printString(
				commentlist_template_path, map, request);
		request.setAttribute("url", htmlString);
		return "/util/urlRedirect";
	}
}
