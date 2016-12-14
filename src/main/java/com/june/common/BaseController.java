package com.june.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
//import org.apache.commons.beanutils.PropertyUtilsBean;
//import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.june.common.converter.DateConverter;
import com.june.common.converter.TimestampConverter;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.dto.back.bussiness.guide.ImageXML;
import com.june.dto.back.bussiness.guide.ImageXML_;
import com.june.dto.back.bussiness.guide.Step;
import com.june.dto.back.bussiness.vehicle.VehicleDto;
import com.june.dto.back.login.ButtonDto;
import com.june.dto.back.system.base.UserInfoDto;
import com.june.service.back.bussiness.ftp.FtpService;
import com.june.service.back.bussiness.vehicle.VehicleService;
import com.june.service.back.common.CommonService;
import com.june.service.back.system.base.userinfo.UserInfoService;
import com.june.utility.DateUtil;
import com.june.utility.MessageUtil;
import com.june.utility.SendMail;
import com.june.utility.exception.CustomException;
import com.june.utility.exception.FastDFSException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
public abstract class BaseController<DTO extends PageDTO<DTO>> {
	//public abstract class BaseController<S extends BaseService<DAO,DTO>,DAO extends BaseDao<DTO>,DTO extends PageDTO<DTO>> {
	
	// 打印log
	protected static Logger logger = LoggerFactory.getLogger(BaseController.class);
	protected static String article_template_path = "template/cmstemplate/article.html";
	protected static String commentlist_template_path = "template/cmstemplate/comment_list.html";
	@Autowired
	public SendMail sendMail;

	@Autowired
	protected UserInfoService userInfoService;
	@Autowired
	protected FtpService ftpService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	protected VehicleService vehicleService;
	@Autowired
	protected CommonService commonService;

	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	
	/**
	 * form表单后台验证
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 * @date 2016年12月14日 下午11:09:34
	 * @writer junehappylove
	 */
	@ModelAttribute
	public DTO validateForm(HttpServletRequest request, HttpServletResponse response,DTO dto)
			throws Exception {
		// 将参数映射到对应的业务dto中并返回
		fillRequestDto(request, dto);
		return dto;
	}
		
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================

	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	//=====================================================================================
	
	/**
	 * 
	 * @return
	 * @date 2016年12月14日 下午11:38:48
	 * @writer junehappylove
	 */
	protected String getProjectName() {
		String hostname = request.getContextPath();
		return hostname;
	}

	protected String getSessionId() {
		return request.getSession().getId();
	}

	/**
	 * 将前台传过来的map类型的参数映射到dto中
	 * 
	 * @param request
	 * @param dest
	 */
	protected void fillRequestDto(HttpServletRequest request, BaseDTO dest) {
		// 注册Date类型
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
		// 注册Timestamp类型
		ConvertUtils.register(new TimestampConverter(), java.sql.Timestamp.class);
		Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
		try {
			BeanUtils.populate(dest, map);
			// PropertyUtilsBean.copyProperties(dest, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将dto转换成map形式
	 * 
	 * @author caiyang
	 * @param bean
	 * @return Map <String,Object>
	 */
	public void beantoMap(Map<String, Object> returnMap, DTO bean) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = null;
				try {
					result = readMethod.invoke(bean, new Object[0]);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
	}

	/**
	 * dto转成json对象传回前台
	 * 
	 * @param response
	 * @param jsonObject
	 * @date 2015年12月16日 上午10:51:53
	 * @writer wjw
	 */
	protected void ConvetDtoToJson(HttpServletResponse response, JSONObject jsonObject) {
		response.setContentType("text/json;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			//logger.debug(jsonObject.toString());
			logger.debug("ConvetDtoToJson");
			out.println(jsonObject.toString());
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	/**
	 * list转成json对象
	 * 
	 * @param response
	 * @param jsonArray
	 * @date 2015年12月16日 上午10:51:14
	 * @writer wjw.happy.love@163.com
	 */
	protected void ConvertListToJson(HttpServletResponse response, JSONArray jsonArray) {
		response.setContentType("text/json;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			//logger.debug(jsonArray.toString());
			logger.debug("ConvertListToJson");
			out.println(jsonArray.toString());
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * dto转成json对象传回前台（上传下载专用）
	 * 
	 * @param response
	 * @param jsonObject
	 * @date 2015年12月16日 上午10:51:05
	 * @writer wjw.happy.love@163.com
	 */
	protected void Converttojsonobjectajax(HttpServletResponse response, JSONObject jsonObject) {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			logger.debug("Converttojsonobjectajax");
			out.println(jsonObject.toString());
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 打印空json到前台
	 * 
	 * @param response
	 * @date 2015年12月11日 下午4:45:01
	 * @writer wjw.happy.love@163.com
	 */
	protected void printJsonNull(HttpServletResponse response) {
		toJson("[{}]", response);
	}

	protected void toJson(Object bean, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(bean);
			response.setContentType("text/Xml;charset=gbk");
			out = response.getWriter();
			//logger.debug(jsonObject.toString());
			out.println(jsonObject.toString());
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	protected void toJson(ImageXML bean, HttpServletResponse response) {
		PrintWriter out = null;
		JSONObject jsonObject = null;
		try {
			TreeSet<Step> set = bean.getStep();
			// TODO Step类有点特别，重写了equal方法
			if(set != null && set.size() == 1){
				Step step = set.first();
				List<Step> list = new ArrayList<>();
				list.add(step);
				ImageXML_ image = new ImageXML_();
				image.setPath(bean.getPath());
				image.setSize(bean.getSize());
				image.setStep(list);
				jsonObject = JSONObject.fromObject(image);
			}else{
				jsonObject = JSONObject.fromObject(bean);
			}
			response.setContentType("text/Xml;charset=gbk");
			out = response.getWriter();
			logger.debug(jsonObject.toString());
			out.println(jsonObject.toString());
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 发送字符串到前台页面
	 * 
	 * @param string
	 * @param response
	 * @date 2015年12月4日 下午2:52:17
	 * @writer wjw.happy.love@163.com
	 */
	protected void toJson(String string, HttpServletResponse response) {
		response.setContentType("text/Xml;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			logger.debug(string);
			out.println(string);
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 发送AbstractDTO 对象json格式到页面
	 * 
	 * @param bean
	 * @param response
	 * @date 2015年12月4日 下午2:52:44
	 */
	protected void toJson(DTO bean, HttpServletResponse response) {
		JSONObject jsonObject = JSONObject.fromObject(bean);
		response.setContentType("text/Xml;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			//logger.debug(jsonObject.toString());
			out.println(jsonObject.toString());
		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 发送List列表对象json格式到页面
	 * 
	 * @param beans
	 * @param response
	 * @date 2015年12月4日 下午2:53:31
	 */
	protected void toJson(List<?> beans, HttpServletResponse response) {
		JSONArray jsonArray = JSONArray.fromObject(beans);
		response.setContentType("text/Xml;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			//logger.debug(jsonArray.toString());
			out.println(jsonArray.toString());

		} catch (IOException ex1) {
			ex1.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 将图片返回到页面显示
	 * 
	 * @param httpServletResponse
	 * @param filePath
	 * @date 2015年12月16日 上午10:50:51
	 * @writer wjw.happy.love@163.com
	 */
	protected void returnImage(HttpServletResponse response, String filePath) {
		FileInputStream fis = null;
		response.setContentType("image/gif");
		try {
			OutputStream out = response.getOutputStream();
			File file = new File(filePath);
			fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 普通邮件发送
	 * @param emails
	 * @param subject
	 * @param text
	 * @param fromEmail
	 * @date 2016年12月14日 下午9:25:29
	 * @writer iscas
	 */
	protected void sendTextEmail(String emails, String subject, String text, String fromEmail) {
		sendMail.sendTextEmail(emails, subject, text, fromEmail);
	}

	/**
	 * html格式邮件发送
	 * @param emails
	 * @param subject
	 * @param text
	 * @param fromEmail
	 * @throws MessagingException
	 * @date 2016年12月14日 下午9:25:35
	 * @writer iscas
	 */
	protected void sendHtmlEmail(String emails, String subject, String text, String fromEmail)
			throws MessagingException {
		sendMail.sendHtmlEmail(emails, subject, text, fromEmail);
	}

	/**
	 * 共同的异常处理
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 * @date 2015年12月16日 上午10:50:26
	 * @writer wjw.happy.love@163.com
	 */
	@ExceptionHandler
	public ModelAndView exception(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws Exception {
		MessageDto messageDto = new MessageDto();
		// 判断是否是ajax请求
		if (isAjaxCall(request)) {
			// String url = request.getRequestURL().toString();
			// String urls[] = url.split("/");
			// String lastUrl = urls[urls.length - 1];
			if (e.getClass().equals(CustomException.class)) {
				String message = e.getMessage();
				String[] messages = message.split(";");
				ArrayList<String> errList = new ArrayList<String>();
				for (int i = 0; i < messages.length; i++) {
					// 添加自己的异常处理逻辑，如日志记录
					logger.error("Exception:" + e + ":" + messages[i]);
					errList.add(messages[i]);
					messageDto.setErrList(errList);
					messageDto.setErrType("error");
				}
			}

			else {
				// 添加自己的异常处理逻辑，如日志记录
				ArrayList<String> errList = new ArrayList<String>();
				try {
					logger.error("Exception:" + e + ":" + e.getMessage());
					errList.add(MessageUtil.formatMessage("sys_error"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				messageDto.setErrList(errList);
				messageDto.setErrType("error");
			}
			JSONObject jsonObject = JSONObject.fromObject(messageDto);
			ConvetDtoToJson(response, jsonObject);
			// 页面不跳转
			return null;
		} else {
			if (e instanceof FastDFSException) {
				ArrayList<String> errList = new ArrayList<String>();
				logger.error("Exception:" + e + ":" + e.getMessage());
				errList.add(MessageUtil.formatMessage("noavaliablearacker"));
				messageDto.setErrList(errList);
				messageDto.setErrType("error");
				// throw new
				// FastDFSException(MessageUtil.getFormatMessage("noavaliablearacker",
				// null));
				toJson(messageDto, response);
				// 页面不跳转
				return null;
			} else {
				ModelAndView result = new ModelAndView("error/error");
				logger.error("Exception:" + e + ":" + e.getMessage());
				// result.addObject("error", e.getMessage());
				return result;
			}
		}
	}

	/**
	 * 判断是否是ajax请求
	 * 
	 * @param request
	 * @return
	 * @date 2015年12月16日 上午10:50:14
	 * @writer wjw.happy.love@163.com
	 */
	public boolean isAjaxCall(HttpServletRequest request) {
		return ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
	}

	/**
	 * 根据字节流返回图片
	 * @param httpServletResponse
	 * @param b
	 * @date 2016年12月14日 下午9:31:42
	 * @writer junehappylove
	 */
	protected void returnImageByBuffer(HttpServletResponse httpServletResponse, byte[] b) {
		httpServletResponse.setContentType("image/gif");
		try {
			OutputStream out = httpServletResponse.getOutputStream();
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日期类型绑定处理 
	 * @param binder
	 * @date 2016年12月14日 下午9:31:51
	 * @writer junehappylove
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(java.sql.Timestamp.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 获取validate错误消息
	 * 
	 * @throws Exception
	 * @author caiyang
	 * @param bindingResult
	 */
	protected MessageDto getValidateError(BindingResult bindingResult) throws Exception {
		MessageDto messageDto = new MessageDto();
		ArrayList<String> errList = new ArrayList<String>();
		if (bindingResult.hasErrors()) {
			List<ObjectError> messageList = bindingResult.getAllErrors();
			for (ObjectError list : messageList) {
				if (list.getDefaultMessage().contains(";")) {
					String[] messageArray = list.getDefaultMessage().split(";");
					String message = messageArray[0];
					String messageParam = "";
					for (int i = 1; i < messageArray.length; i++) {
						messageParam = messageParam + messageArray[i] + ",";
					}
					if (messageParam.contains(",")) {
						errList.add(setMessageByParam(message, messageParam.split(",")));
					}
				} else {
					errList.add(list.getDefaultMessage());
				}
			}
			messageDto.setErrList(errList);
			messageDto.setErrType(MESSAGE_ERRO);
		}
		return messageDto;
	}

	/**
	 * 设定message
	 * 
	 * @param message
	 *            MessagesDTO
	 * @param messageParam
	 *            String[]
	 * @return String
	 * @throws SpaceParameterException
	 *             异常信息
	 */
	private static String setMessageByParam(String message, String[] messageParam) throws Exception {
		MessageFormat messageFormat = new MessageFormat(message);
		String messageValue = messageFormat.format(messageParam);
		return messageValue;
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @param request
	 * @return
	 */
	public UserInfoDto loginUser(HttpServletRequest request) {
		UserInfoDto userInfoDto = (UserInfoDto) request.getSession().getAttribute("userInfo");
		return userInfoDto;
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @return UserInfoDto
	 */
	public UserInfoDto loginUser() {
		UserInfoDto userInfoDto = (UserInfoDto) request.getSession().getAttribute("userInfo");
		return userInfoDto;
	}

	/**
	 * 设置当前操作人
	 * 
	 * @param view
	 * @param bean
	 * @param request
	 */
	public void setCreater(DTO bean, HttpServletRequest request) {
		UserInfoDto userInfoDto = this.loginUser(request);
		String userId = userInfoDto.getUserId();
		bean.setAddUserId(userId);// 设置操作人id
		bean.setUpdateUserId(userId);// 设置操作人id
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if (bean.getAddTime() == null) {
			bean.setAddTime(time);// 设置时间
		}
		if (bean.getUpdateTime() == null) {
			bean.setUpdateTime(time);// 设置时间
		}
	}

	/**
	 * 设置当前修改人
	 * 
	 * @param view
	 * @param bean
	 * @param request
	 */
	public void setUpdater(DTO bean, HttpServletRequest request) {
		UserInfoDto userInfoDto = this.loginUser(request);
		String userId = userInfoDto.getUserId();
		bean.setUpdateUserId(userId);// 设置操作人id
		Timestamp time = new Timestamp(System.currentTimeMillis());
		bean.setUpdateTime(time);// 设置时间
		if (bean.getAddTime() == null) {
			bean.setAddTime(time);
		}
		if (bean.getAddUserId() == null) {
			bean.setAddUserId(userId);
		}
	}

	/**
	 * 新页面初始化数据
	 * 
	 * @param page
	 * @param request
	 */
	public void htmlPageInit(ModelAndView page, HttpServletRequest request) {
		UserInfoDto user = this.loginUser(request);
		String userId = user.getUserId();
		String username = user.getUserName();
		page.addObject("addUserName", username);
		page.addObject("updateUserName", username);
		page.addObject("addUserId", userId);
		page.addObject("updateUserId", userId);
		page.addObject("addTime", DateUtil.getTimeOfNow("yyyy-MM-dd HH:mm:ss"));
		page.addObject("updateTime", DateUtil.getTimeOfNow("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 处理编辑页面初始化
	 * 
	 * @param page
	 * @param bean
	 */
	public void htmlEditInit(ModelAndView page, DTO bean) {
		UserInfoDto user = userInfoService.getDtoById(new UserInfoDto(bean.getUpdateUserId()));
		page.addObject("updateUserName", user != null ? user.getUserName() : "未知用户");
		// TODO 约定： 这里默认使用bean存放，或者开发人员在自己业务里自定义
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if (bean.getUpdateTime() == null) {
			bean.setUpdateTime(time);
		}
		if (bean.getAddTime() == null) {
			bean.setAddTime(time);
		}
		page.addObject("bean", bean);//
	}

	/**
	 * 设置其他信息到前台页面中
	 * 
	 * @param page
	 * @param bean
	 * @param name
	 * @date 2015年12月16日 下午3:02:39
	 * @writer wjw.happy.love@163.com
	 */
	public void setOtherInfo(ModelAndView page, Object bean, String name) {
		page.addObject(name, bean);//
	}

	/**
	 * 保存成功消息
	 * 
	 * @param response
	 * @throws Exception
	 * @datetime 2015年12月8日 下午1:08:32
	 */
	public void messageSaveSuccess(HttpServletResponse response) throws Exception {
		String messages = "save_success";
		String type = MESSAGE_INFO;
		throwMessage(response,messages, type);
	}

	/**
	 * 发送成功消息
	 * 
	 * @param response
	 * @throws Exception
	 * @datetime 2015年12月8日 下午1:08:45
	 */
	public void messageSendSuccess(HttpServletResponse response) throws Exception {
		String messages = "send_success";
		String type = MESSAGE_INFO;
		throwMessage(response,messages, type);
	}

	/**
	 * 删除成功消息
	 * 
	 * @param response
	 * @throws Exception
	 * @datetime 2015年12月8日 下午1:09:00
	 */
	public void messageDeleteSuccess(HttpServletResponse response) throws Exception {
		String messages = "delete_success";
		String type = MESSAGE_INFO;
		throwMessage(response,messages, type);
	}

	/**
	 * 更新数据成功消息
	 * 
	 * @param response
	 * @throws Exception
	 * @date 2015年12月30日 下午2:09:37
	 * @writer wjw
	 */
	public void messageUpdateSuccess(HttpServletResponse response) throws Exception {
		String messages = "update_success";
		String type = MESSAGE_INFO;
		throwMessage(response,messages, type);
	}
	
	/**
	 * 处理消息
	 * @param response
	 * @param messages 
	 * @param type [MESSAGE_INFO,MESSAGE_ERRO,MESSAGE_WARN,MESSAGE_QUES]
	 * @param params 消息的替换参数
	 * @throws Exception
	 * @date 2016年12月14日 下午11:56:32
	 * @writer junehappylove
	 */
	public void throwMessage(HttpServletResponse response,String messages, String type, String... params) throws Exception {
		ArrayList<String> errList = new ArrayList<String>();
		message = new MessageDto();
		// message.setErrList(null);
		errList.add(MessageUtil.formatMessage(messages,params));
		message.setErrList(errList);
		message.setErrType(type);
		// 返回消息 end
		toJson(message, response);
	}

	public MessageDto message;
	/** 提示 */
	public static String MESSAGE_INFO = "info";
	/** 错误 */
	public static String MESSAGE_ERRO = "error";
	/** 警告 */
	public static String MESSAGE_WARN = "warning";
	/** 疑问 */
	public static String MESSAGE_QUES = "question";

	/**
	 * 将json数据转换成对象列表
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public List<?> data2List(String data, Class<?> clazz) {
		data = "[" + data + "]";
		JSONArray jas = JSONArray.fromObject(data);
		@SuppressWarnings("deprecation")
		List<?> list = JSONArray.toList(jas, clazz);
		return list;
	}

	public void messageErrorExist(HttpServletResponse response) throws Exception {
		String messages = "error_exist";
		String type = MESSAGE_ERRO;
		throwMessage(response,messages, type);
	}

	public void messageErrorNotExist(HttpServletResponse response) throws Exception {
		String messages = "error_not_exist";
		String type = MESSAGE_ERRO;
		throwMessage(response,messages, type);
	}

	public ModelAndView initPage(HttpServletRequest request, String page) {
		ModelAndView result = null;
		result = new ModelAndView(page);
		// 获取用户信息
		UserInfoDto userInfoDto = loginUser(request);
		ButtonDto buttonDto = new ButtonDto();
		if (userInfoDto != null) {
			buttonDto.setRoleId(userInfoDto.getRoleId());
			buttonDto.setMenuUrl(request.getServletPath());
			// 根据故障代码角色和初始化的页面获取该页面有权限的操作
			List<ButtonDto> list = commonService.getFunctionByRole(buttonDto);
			for (int i = 0; i < list.size(); i++) {
				result.addObject(list.get(i).getButtonPageId(), "hasAuthority");
			}
		}
		return result;
	}

	public void initDto(AbstractDTO dto) {
		dto.setAddTime(new Timestamp(System.currentTimeMillis()));
		dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		dto.setAddUserId(this.loginUser().getUserId());
		dto.setUpdateUserId(this.loginUser().getUserId());
	}

	/**
	 * 判断是否包含某格式
	 * 
	 * @param type
	 * @return
	 * @date 2016年6月28日 下午2:23:13
	 * @writer wjw.happy.love@163.com
	 */
	public boolean can(String type) {
		String[] types = { ".jpg", ".gif", ".png", ".bmp", ".mp4", ".swf" };
		type = type.toLowerCase();
		boolean result = false;
		for (String s : types) {
			if (type.contains(s)) {
				result = true;
				break;
			} else {
				continue;
			}
		}
		return result;
	}

	/**
	 * 下载文件
	 * 
	 * @param filePath
	 * @param response
	 * @throws IOException
	 * @date 2016年6月29日 下午6:50:54
	 * @writer wjw.happy.love@163.com
	 */
	public void download(String filePath, HttpServletResponse response) throws IOException {
		OutputStream out = null;
		InputStream in = null;
		try {
			File file = new File(filePath);
			if (file != null) {
				response.setContentType("application/octet-stream; charset=utf-8");
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("gbk"), "iso-8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				ServletOutputStream outputStream = response.getOutputStream();
				// 以流的形式下载文件
				in = new FileInputStream(file);
				out = outputStream;
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					out.write(b, 0, i);
				}
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.close();
		} // */
	}
	
	public String getVehiclePath(String vehicleId) {
		VehicleDto vd = vehicleService.getDtoById(new VehicleDto(vehicleId));
		return vd != null ? vd.getFtpPath() : "";
	}
	
	/**
	 * 将ImageXML文件对象转换成流
	 * @param xml
	 * @return
	 * @date 2016年7月4日 下午10:35:29
	 * @writer wjw.happy.love@163.com
	 */
	public InputStream bean2Stream(ImageXML xml){
		if (xml != null && xml.getStep().size() > 0) {
			String name = String.valueOf(System.currentTimeMillis()) + ".xml";
			// TODO 这个文件的定义 : "xml.xml" 固定文件名，多用户同时操作可能会有错误
			File file = new File(Constants.DIRECTORY_LOCAL_DOWNLOAD + File.separator + name);
			InputStream inputStream = null;
			try {
				JAXBContext context = JAXBContext.newInstance(ImageXML.class);
				Marshaller marshaller = context.createMarshaller();
				// marshaller.marshal(xml, System.out);
				marshaller.marshal(xml, file);
				inputStream = new FileInputStream(file);
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return inputStream;
		} else {
			return null;
		}
	}

	/**
	 * 将流数据转换成ImageXML文件对象
	 * @param content
	 * @return
	 * @date 2016年7月4日 下午10:35:52
	 * @writer wjw.happy.love@163.com
	 */
	public ImageXML xml2Bean(InputStream content){
		ImageXML xml = null;
		try {  
            JAXBContext context = JAXBContext.newInstance(ImageXML.class);  
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xml = (ImageXML)unmarshaller.unmarshal(content);
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }
		return xml;
	}
	
	/**
	 * 将xml内容转换成对象
	 * @param content
	 * @return
	 * @date 2016年7月4日 下午10:36:20
	 * @writer wjw.happy.love@163.com
	 */
	public ImageXML xml2Bean(String content){
		ImageXML xml = null;
		try {  
            JAXBContext context = JAXBContext.newInstance(ImageXML.class);  
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xml = (ImageXML)unmarshaller.unmarshal(new StringReader(content));
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }
		return xml;
	}

	/**
	 * 整理一个目录地址形式<br>
	 * 标准形式格式：/xxx/yyyy/zzzz<br>
	 * 开头/ , 最后没有/
	 * @param ftpPath
	 * @return
	 * @date 2016年7月6日 下午3:33:28
	 * @writer wjw.happy.love@163.com
	 */
	public String $dir(String ftpPath) {
		if(StringUtils.isNotEmpty(ftpPath)){
			ftpPath = ftpPath.replaceAll("//", "/");
			if(ftpPath.charAt(0) != '/'){
				ftpPath = "/"+ftpPath;
			}
			if(ftpPath.charAt(ftpPath.length()-1)=='/'){
				ftpPath = ftpPath.substring(0,ftpPath.length()-1);
			}
			return ftpPath;
		}else
			return "";
	}
	
	/**
	 * 获取操作步骤
	 * @param path
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * @date 2016年7月6日 下午9:57:03
	 * @writer wjw.happy.love@163.com
	 */
	public ImageXML getImageXmlFromPath(String path) throws SocketException, IOException {
		String xmlName = Constants.FILE_STEP;// xml路径以及名称
		FtpDto ftp = new FtpDto();
		ftp.setFtpPath($dir(path));
		ftp.setFtpFileName(xmlName);//
		InputStream is = ftpService.fileStream(ftp);
		ImageXML imageXml = null;
		if (is != null) {
			// 如果原始信息存在
			imageXml = this.xml2Bean(is);
		}
		return imageXml;
	}
}
