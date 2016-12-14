package com.june.controller.front.portalinit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.june.common.BaseController;
import com.june.dto.front.portalinit.ArticleDto;
import com.june.service.front.portalinit.PortalInitService;

/**  
* @Description: 门户首页初始化用controller
* @author caiyang
* @date 2015年11月5日 上午9:30:35 
* @version V1.0  
 */
@Controller
//@RequestMapping("/index")
public class PortalInitController extends BaseController<ArticleDto>{

	/**
	 * 门户首页初始化用service注入
	 */
	@Autowired
	protected PortalInitService portalInitService;
	/**   
	 * @Description: 门户首页初始化
	 * @author	caiyang
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws   
	 */
	@RequestMapping("/index")
	public ModelAndView frontIndexinit()
	{
		ModelAndView result = null;
		result = new ModelAndView("front/index");
		ArticleDto articleDto = new ArticleDto();
		//获取已经审批通过的文章
		//TODO 具体的模块分类
		List<ArticleDto> list = portalInitService.getArtical(articleDto);
		result.addObject("list",list);
		return result;
		
	}
}
