/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.controller.back.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.june.common.BaseController;
import com.june.common.MessageDto;
import com.june.dto.back.login.MenuDto;
import com.june.dto.back.system.basicset.UserInfoDto;
import com.june.dto.back.system.basicset.UserRoleDto;
import com.june.service.back.login.LoginService;
import com.june.service.back.system.basicset.userinfo.UserInfoService;
import com.june.utility.MessageUtil;
import com.june.utility.exception.LoginAttemptException;
import com.google.code.kaptcha.Producer;

import net.sf.json.JSONObject;
@Controller
public class LoginController extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	/**
	 * 登录用service注入
	 */
	@Autowired
	protected LoginService loginService;


	/**
	 * 用户信息 service
	 */
	@Autowired
	protected UserInfoService userInfoService;

	@Autowired
	private Producer captchaProducer = null;
	
//	@Autowired
//	private ShardedJedisPool shardedJedisPool; 

	/**
	 * @Description: 进入登录页面
	 * @param
	 * @return ModelAndView
	 * @throws
	 */
//	@RequestMapping("/")
//	public ModelAndView login() {
//		ModelAndView result = null;
//		result = new ModelAndView("login/login");
//		return result;
//	}
//
//	/**
//	 * @Description: 进入登录页面第二个url副口
//	 * @param
//	 * @return ModelAndView
//	 * @throws
//	 */
//	@RequestMapping("/login/")
//	public ModelAndView secondLogin() {
//		ModelAndView result = null;
//		result = new ModelAndView("login/login");
//		return result;
//	}
	
	/**
	 * 用户登录验证入口
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 * @date 2016年10月21日 下午8:17:42
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/logincheck")
	public String checkLogin(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,RedirectAttributes redirectAttributes) throws Exception{
		String username = httpServletRequest.getParameter("username");
	    String password = httpServletRequest.getParameter("password");
	    //String kaptchaFormjsp = httpServletRequest.getParameter("kaptcha");//获取页面传过来的额验证码
	    //String kaptchaExpected = (String) httpServletRequest.getSession().getAttribute("kaptcha");//获取session中的验证码
	    Subject subject = SecurityUtils.getSubject();
	    UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		token.setRememberMe(false);
		UserInfoDto userInfoDto = new UserInfoDto();
		 try {
			  subject.login(token);
			  
		    }catch (UnknownAccountException e) {
		      token.clear();
		      redirectAttributes.addFlashAttribute("errormsg", MessageUtil.getFormatMessage("user_not_exist", null));
			  return  "redirect:/login/"; 
		    }catch (LockedAccountException e) {
		      token.clear();
		      redirectAttributes.addFlashAttribute("errormsg", MessageUtil.getFormatMessage("user_locked", null));
			  return  "redirect:/login/"; 
		    }catch (IncorrectCredentialsException e) {
		      token.clear();
		      userInfoDto.setUserId(username);
		      loginService.updateFailLoginAttempt(userInfoDto);
		      redirectAttributes.addFlashAttribute("errormsg", MessageUtil.getFormatMessage("user_not_exist", null));
			  return  "redirect:/login/"; 
			}catch (LoginAttemptException e) {
				token.clear();
			    redirectAttributes.addFlashAttribute("errormsg", e.getMessage());
				return  "redirect:/login/"; 
			}
	     userInfoDto.setUserId(username);
		 loginService.updateSuccessLoginAttempt(userInfoDto);
		 httpServletRequest.getSession().setAttribute("username",username);
		 //限制只能一个用户登录 start
//		 String sessionId = shardedJedisPool.getResource().get(username);
//		 if (sessionId != null) {
//			//如果该sessionId已经存在，则删除该sessionid
//			 shardedJedisPool.getResource().del("shiro_redis_session:" + sessionId);
//		 }
//		 shardedJedisPool.getResource().set(username,httpServletRequest.getSession().getId());
		//限制只能一个用户登录 end
		 return "redirect:/login/main"; 
	    
	}
	
	
	/**
	 * 主界面入口
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 * @date 2016年10月21日 下午8:18:03
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/login/main")
	public ModelAndView loginto(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		String username = (String) httpServletRequest.getSession().getAttribute("username");
		String roleId = httpServletRequest.getParameter("roleId");// 获取角色选择页面的roleId
		String roleName = httpServletRequest.getParameter("roleName");// 获取角色选择页面的roleName
		// 根据用户id获取用户的角色
		UserInfoDto userInfoDto1 = new UserInfoDto();
		userInfoDto1.setUserId(username);
		// 获取用户角色
		List<UserInfoDto> list = loginService.getRoleInfoByUserId(userInfoDto1);
		ModelAndView result = null;
		if (roleId == null && list != null && list.size() > 1) {
			// 进入角色选择画面
			result = new ModelAndView("main/selectRole");
			result.addObject("username", username);
			result.addObject("roleList", list);
			// return result;
		} else if ((list != null && list.size() == 1) || roleId != null) {
			UserInfoDto userInfoDto = new UserInfoDto();
			userInfoDto = loginService.getUserInfoById(username);
			List<UserRoleDto> roles = null;//用户的角色信息
			if (roleId != null) {
				roles = userInfoService.getRolesById(userInfoDto);
				userInfoDto.setRoleId(roleId);
				userInfoDto.setRoleName(roleName);
			} else {
				userInfoDto.setRoleId(list.get(0).getRoleId());
				userInfoDto.setRoleName(list.get(0).getRoleName());
				roles = userInfoService.getRolesById(userInfoDto);// add by wjw 
			}

			// 根据用户id获取用户信息
			httpServletRequest.getSession().setAttribute("userInfo", userInfoDto);
			httpServletRequest.getSession().setAttribute("roles", roles);
			// 一级菜单取得
			List<MenuDto> firstMenu = loginService.getFristMenu(userInfoDto);
			firstMenu = getMenus(firstMenu);
			// 获取角色全部的按钮权限并放到session中
			
//			List<ButtonDto> buttonList = loginService.getRoleButton(userInfoDto);
//			for (int i = 0; i < buttonList.size(); i++) {
//				httpServletRequest.getSession().setAttribute(buttonList.get(i).getButtonPageId(), "hasAuthority");
//			}
			 
			httpServletRequest.getSession().setAttribute("firstMenu", firstMenu);
			result = new ModelAndView("main/main");//成功后跳转到的主页
			result.addObject("firstMenu", firstMenu);
			if (list.size() > 1) {
				result.addObject("switchRole", "switchRole");
			}
		}
		result.addObject("key", "value");

		return result;
	}

	@RequestMapping("/login/menu")
	public ModelAndView menu(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = null;
		result = new ModelAndView("main/menu");//成功后跳转到的主页
		return result;
	}

	@RequestMapping("/login/body")
	public ModelAndView body(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView result = null;
		result = new ModelAndView("main/body");//成功后跳转到的主页
		return result;
	}
	
	/**
	 * 根据一级菜单递归获取二级菜单
	 * @param firstMenu
	 * @return
	 * @date 2016年10月21日 下午6:36:43
	 * @writer wjw.happy.love@163.com
	 */
	private List<MenuDto> getMenus(List<MenuDto> firstMenu) {

		for (int i = 0; i < firstMenu.size(); i++) {
			firstMenu.get(i).setMenus(loginService.GetSecondMenu(firstMenu.get(i)));
			getMenus(firstMenu.get(i).getMenus());
		}
		return firstMenu;
	}
	
	
	/**
	 * 没有权限返回页面
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 * @date 2016年10月21日 下午6:36:49
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/denied")
	public ModelAndView noauthority(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView result = null;
		
		result = new ModelAndView("error/403");
		String msg = MessageUtil.getFormatMessage("access_denied", null);
		// 将错误信息返回到页面
		result.addObject("error", msg);
		return result;
	}
	
	
	/**
	 * 切换登录角色
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @date 2016年10月21日 下午6:37:33
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/login/changeRole")
	public ModelAndView changeRole(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		// 获取用户信息
		UserInfoDto userInfoDto = (UserInfoDto) httpServletRequest.getSession()
				.getAttribute("userInfo");
		
		List<UserInfoDto> roleList = loginService.getRoleInfoByUserId(userInfoDto);
		ModelAndView result = null;
		// 进入角色选择画面
		result = new ModelAndView("main/selectRole");
		result.addObject("roleList", roleList);
		result.addObject("username", userInfoDto.getUserId());
		return result;
	}


	/**
	 * 登录后面直接输入url返回的页面
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 * @date 2016年10月21日 下午6:37:41
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/login/error")
	public ModelAndView error(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ModelAndView result = null;
		// 获取错误代码
		String errorCode = httpServletRequest.getParameter("error");
		if (errorCode.equals("401")) {
			result = new ModelAndView("error/InvalidSessionError");
		}
		else if(errorCode.equals("403"))
		{
			result = new ModelAndView("error/403");
		}else if (errorCode.endsWith("500")) {
			result = new ModelAndView("error/internal-server-error");
		}
		return result;
	}
	

	/**
	 * app登录成功，返回sessionid
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @date 2016年10月21日 下午6:38:04
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/login/success")
	public void success(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sessionId = req.getSession().getId();
		JSONObject jObject = new JSONObject();
		jObject.put("status", "0");// 登录成功
		jObject.put("sessionId", sessionId);// sessionid
		PrintWriter pw = resp.getWriter();
		pw.print(jObject);
	}

	/**
	 * app登录失败，返回appfail
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @date 2016年10月21日 下午6:38:11
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/login/failure")
	public void failure(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter pw = resp.getWriter();
		JSONObject jObject = new JSONObject();
		jObject.put("status", "1");// 用户名或密码错误返回1
		pw.print(jObject);
	}


	/**
	 * 生成页面显示用验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @date 2016年10月21日 下午6:38:17
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/getKaptchaImage")
	public ModelAndView getKaptchaImage1(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		// String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		session.setAttribute("kaptcha", capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 验证验证码是否输入正确
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @date 2016年10月21日 下午6:38:45
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/isKaptchaRight")
	public void isKaptchaRight(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String kaptcha = (String) httpServletRequest.getParameter("kaptcha");
		String capText = (String) httpServletRequest.getSession().getAttribute(
				"kaptcha");// 获取session里的验证码

		MessageDto messageDto = new MessageDto();
		if (kaptcha.equalsIgnoreCase(capText) || kaptcha.equals(capText)
				|| kaptcha == capText) {
			messageDto.setErrType("info");
		} else {
			messageDto.setErrType("error");
		}
		JSONObject jsonObject = JSONObject.fromObject(messageDto);
		ConvetDtoToJson(httpServletResponse, jsonObject);
	}
	
	@RequestMapping("/app/login")
	public void applogin(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException
	{
		String username = httpServletRequest.getParameter("j_username");
	    String password = httpServletRequest.getParameter("j_password");
	    Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		token.setRememberMe(false);
		 try {
			  subject.login(token);
		      UserInfoDto userInfoDto = new UserInfoDto();
		      userInfoDto = loginService.getUserInfoById(username);
		      //根据用户id获取用户信息
		      httpServletRequest.getSession().setAttribute("userinfodto", userInfoDto);
		      httpServletRequest.getSession().setAttribute("LOGIN_SUCCESS","success");
		      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		      System.out.println(httpServletRequest.getSession().getId());
		      httpServletResponse.getWriter().write(httpServletRequest.getSession().getId());
		    }catch (Exception e) {
		      e.printStackTrace();
		      token.clear();
		      httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
		      httpServletResponse.getWriter().write("login error");
		    }
	}
	
}
