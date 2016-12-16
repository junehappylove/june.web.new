/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.controller.back.demo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.june.common.AbstractDTO;
import com.june.common.BaseController;
import com.june.common.MessageDto;
import com.june.dto.back.demo.ComboxDto;
import com.june.dto.back.demo.DemoDto;
import com.june.dto.back.demo.ExcelDto;
import com.june.dto.back.demo.MenuDto;
import com.june.dto.back.demo.SchemaDto;
import com.june.dto.back.demo.TreeDto;
import com.june.dto.back.demo.TreeGridDto;
import com.june.service.back.demo.DemoService;
import com.june.utility.CheckUtil;
import com.june.utility.DbUtil;
import com.june.utility.ExportImportExcel;
import com.june.utility.FastDfsUtils;
import com.june.utility.FileUpLoadDownload;
import com.june.utility.MessageUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController<MenuDto> {

	@Autowired
	DemoService demoService;

	@Autowired
	private Producer captchaProducer = null;

	@ModelAttribute
	public AbstractDTO validateForm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DemoDto demoDto = new DemoDto();
		fillRequestDto(request, demoDto);
		return demoDto;
	}

	@RequestMapping("/demoInit")
	public ModelAndView demoInit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = null;
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		result = new ModelAndView("demo/demo");
		// 前台select数据start
		result.addObject("checkbox3", "1");
		result.addObject("radio3", "1");
		ComboxDto comboxDto = new ComboxDto();
		comboxDto.setCode("1");
		comboxDto.setName("岗位1");
		ComboxDto comboxDto2 = new ComboxDto();
		comboxDto2.setCode("2");
		comboxDto2.setName("岗位2");
		List<ComboxDto> list = new ArrayList<ComboxDto>();
		list.add(comboxDto);
		list.add(comboxDto2);
		result.addObject("list", list);
		result.addObject("title", "2");
		// 前台combox数据end
		return result;
	}

	@RequestMapping("/demo")
	public void demo(@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response, @Valid DemoDto demoDto, BindingResult bindingResult)
					throws Exception {
		MessageDto messageDto = getValidateError(bindingResult);
		// 判断是否有参数验证错误
		if (!messageDto.getErrType().isEmpty()) {
			// 有错误返回
			toJson(messageDto, response);
		} else {
			// 没错误执行业务逻辑
			// TODO
		}
		// DemoDto demoDto = new DemoDto();
		// fillRequestDto(request, demoDto);
		// System.out.println("aaaa");
		// MessageDto messageDto = new MessageDto();
		// //返回消息 start
		// @SuppressWarnings("rawtypes")
		// ArrayList errList = new ArrayList();
		// errList.add(MessageUtil.getFormatMessage("export_success", null));
		// messageDto.setErrList(errList);
		// messageDto.setErrType("info");
		// //返回消息 end
		// JSONObject jsonObject = JSONObject.fromObject(messageDto);
		// ConvetDtoToJson(response, jsonObject);

	}

	@RequestMapping("/importData")
	public void importData(@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MessageDto messageDto = new MessageDto();
		ArrayList<String> errList = new ArrayList<String>();
		// String realPath =
		// request.getSession().getServletContext().getRealPath("/upload");
		String originalFilename = null;
		InputStream inputStream = null;
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				errList.add("请选择文件");
				messageDto.setErrList(errList);
			} else {
				originalFilename = myfile.getOriginalFilename();
				// 判断文件是否是excel文件
				String end = originalFilename.substring(originalFilename.length() - 4, originalFilename.length());
				if (end.contains("xls")) {
					inputStream = FileUpLoadDownload.getFileStream(myfile);
				} else {
					errList.add("请选择Excel格式的文件！");
					messageDto.setErrList(errList);
					messageDto.setErrType("error");
				}

			}
		}
		if (errList.size() <= 0) {
			ExcelDto excelDto = new ExcelDto();
			String[] attrs = { "no", "name", "dept", "title", "date", "onTime", "offTime", "address", "getDate",
					"carNo", "status" };
			int[] colomns = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
			// String path = realPath + "\\" + originalFilename;
			// 读取excel中的数据
			@SuppressWarnings("unchecked")
			List<ExcelDto> list = (List<ExcelDto>) ExportImportExcel.readXls(1, inputStream, excelDto, colomns, attrs);
			if (list.size() > 0) {
				// 进行check
				for (int i = 0; i < list.size(); i++) {
					CheckUtil.checkMust(list.get(i).getNo(), "编号", String.valueOf(i + 1), errList);
					CheckUtil.checkDate(list.get(i).getDate(), "打卡日期", "yyyy-MM-dd", String.valueOf(i + 1), errList);
				}
				if (errList.size() > 0) {
					messageDto.setErrList(errList);
					messageDto.setErrType("error");
				}
				// 进行插入操作
			} else {
				errList.add("文件中没有数据！");
				messageDto.setErrList(errList);
				messageDto.setErrType("error");
			}

		}
		// 返回消息 end
		toJson(messageDto, response);
	}

	/**
	 * 导出文件，直接以页面下载的方式导出
	 * @param request
	 * @param response
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @date 2016年6月27日 下午1:42:36
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/exportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		ExcelDto excelDto = new ExcelDto();
		excelDto.setAddress("aaa");
		excelDto.setCarNo("aaa66");
		excelDto.setDate("2015-01-02");
		excelDto.setDept("dept1");
		excelDto.setGetDate("2015-01-02");
		excelDto.setName("admin");
		excelDto.setNo("11");
		excelDto.setOffTime("20:10");
		excelDto.setOnTime("9:00");
		excelDto.setTitle("title1");
		excelDto.setStatus("aaa");

		ExcelDto excelDto2 = new ExcelDto();
		excelDto2.setAddress("aaa1");
		excelDto2.setCarNo("aaa661");
		excelDto2.setDate("2015-01-02");
		excelDto2.setDept("dept11");
		excelDto2.setGetDate("2015-01-02");
		excelDto2.setName("admin1");
		excelDto2.setNo("12");
		excelDto2.setOffTime("20:10");
		excelDto2.setOnTime("8:00");
		excelDto2.setTitle("title2");
		excelDto2.setStatus("aaa");
		List<ExcelDto> list = new ArrayList<ExcelDto>();
		list.add(excelDto);
		list.add(excelDto2);
		String[] titles = { "编号", "姓名", "部门", "岗位", "打卡日期", "上班时间", "下班时间", "卡钟地址", "采集日期", "车牌号", "处理状态" };
		String[] attrs = { "no", "name", "dept", "title", "date", "onTime", "offTime", "address", "getDate", "carNo",
				"status" };
		ExportImportExcel.exportExcelForDownload(list, titles, attrs, response, "test", "aa");
	}

	/**
	 * 导出文件到指定的文件夹，导出的路径在config.properties文件中设置
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年6月27日 下午1:43:01
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/exporttofolder")
	public void exporttofolder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExcelDto excelDto = new ExcelDto();
		excelDto.setAddress("aaa");
		excelDto.setCarNo("aaa66");
		excelDto.setDate("2015-01-02");
		excelDto.setDept("dept1");
		excelDto.setGetDate("2015-01-02");
		excelDto.setName("admin");
		excelDto.setNo("11");
		excelDto.setOffTime("20:10");
		excelDto.setOnTime("9:00");
		excelDto.setTitle("title1");
		excelDto.setStatus("aaa");

		ExcelDto excelDto2 = new ExcelDto();
		excelDto2.setAddress("aaa1");
		excelDto2.setCarNo("aaa661");
		excelDto2.setDate("2015-01-02");
		excelDto2.setDept("dept11");
		excelDto2.setGetDate("2015-01-02");
		excelDto2.setName("admin1");
		excelDto2.setNo("12");
		excelDto2.setOffTime("20:10");
		excelDto2.setOnTime("8:00");
		excelDto2.setTitle("title2");
		excelDto2.setStatus("aaa");
		List<ExcelDto> list = new ArrayList<ExcelDto>();
		list.add(excelDto);
		list.add(excelDto2);
		String[] titles = { "编号", "姓名", "部门", "岗位", "打卡日期", "上班时间", "下班时间", "卡钟地址", "采集日期", "车牌号", "处理状态" };
		String[] attrs = { "no", "name", "dept", "title", "date", "onTime", "offTime", "address", "getDate", "carNo",
				"status" };
		ExportImportExcel.exportExcelToFolder(list, titles, attrs, "test",
				MessageUtil.$KEY("file.downloadpath"), "aaa");

		MessageDto messageDto = new MessageDto();
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.$VALUE("export_success"));
		messageDto.setErrList(errList);
		messageDto.setErrType("info");
		// 返回消息 end
		toJson(messageDto, response);
	}

	@RequestMapping("/throwException")
	public void throwException() {
		demoService.delete();
	}

	// 获取多个schema的数据
	@RequestMapping("/getAllSchemaData")
	public void getAllSchemaData(HttpServletRequest request, HttpServletResponse response) {
		request.getSession(false).removeAttribute("token");
		// 获取所有的schema
		List<SchemaDto> list = demoService.getAllSchemaData();
		MenuDto menuDto = new MenuDto();
		// 将前台参数映射到dto
		fillRequestDto(request, menuDto);
		// 声明一个map类型的list
		List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
		// 要查询的表
		@SuppressWarnings("unused")
		String[] strings = new String[] { "sys_menu", "sys_role_menu" };
		for (int i = 0; i < list.size(); i++) {
			// 设置要检索的表 shcema名.表名
			Map<String, Object> params = new HashMap<String, Object>();
			// 如有其它参数需要单独添加
			// 例如：params.put("userId", "userId");
			// this.setSchemaTable(null, list.get(i).getSchemaName(), strings,
			// params);
			listMaps.add(params);
		}

		// menuDto.setSchemaTableList(listMaps);
		// 检索数据
		menuDto = demoService.getMenus(menuDto);
		toJson(menuDto, response);
	}

	// 上传图片
	@RequestMapping("/uploadPic")
	// 如果是多个文件的话用MultipartFile[] myfiles接收参数，如果就一个文件，用MultipartFile myfile接收即可
	public void uploadPic(@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MessageDto messageDto = new MessageDto();

		// 获取文件保存的路径，路径在配置文件config.properties文件中保存
		String realPath = MessageUtil.$KEY("savePicUrl");
		String message = FileUpLoadDownload.uploadSingleFile(myfiles[0], request, response,
				realPath, null);
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(message);
		// 图片存储在服务器的路径
		// 如果未自定义名字则用FileUpLoadDownload.getFileName(myfiles)方法获取文件名字，
		// 否则自己拼接，String filePath = realPath + "aaa.jpg";
		//String filePath = realPath + FileUpLoadDownload.getFileName(myfiles[0], "");
		//String md5 = MD5Util.getFileMD5String(FileUpLoadDownload.getFileStream(myfiles[0]));
		// TODO 将路径保存到db操作
		messageDto.setErrList(errList);
		messageDto.setErrType("info");
		toJson(messageDto, response);
	}

	// 获取图片
	@RequestMapping("/getImage")
	public void getImage(HttpServletRequest request, HttpServletResponse response) {
		String realPath = MessageUtil.$KEY("savePicUrl");
		String filePath = realPath + "22.png";
		returnImage(response, filePath);
	}

	// fastdfs上传图片文件
	@RequestMapping("/uploadpicture")
	public void uploadpicture(@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 循环进行文件上传处理
		for (int i = 0; i < myfiles.length; i++) {

			String filename = FileUpLoadDownload.getFileName(myfiles[0], null);
			InputStream inputStream = FileUpLoadDownload.getFileStream(myfiles[0]);
			// 将文件上传到fastdfs服务器并且返回服务器保存地址
			@SuppressWarnings("unused")
			String remoteFileAddress = FastDfsUtils.uploadFile(inputStream, filename, "caiyang");
			// TODO 业务操作
		}

		MessageDto messageDto = new MessageDto();
		// TODO
		toJson(messageDto, response);
	}

	// fastdfs获取图片文件
	@RequestMapping("/getPicture")
	public void getPicture(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO 从db中获取图片路径
		// 根据路径获取图片
		byte[] buffer = FastDfsUtils.getFile("group1:M00/00/00/CjLIrVYgET2AHxNAAADTg_tD710250.png");
		returnImageByBuffer(response, buffer);
	}

	// 树结构数据初始化
	@RequestMapping("/initTree")
	public void initTree(HttpServletRequest request, HttpServletResponse response) {
		TreeDto treeDto = new TreeDto();
		treeDto.setId("1");
		treeDto.setName("bb");
		treeDto.setOpen(true);
		treeDto.setChecked(true);
		TreeDto treeDto2 = new TreeDto();
		treeDto2.setId("2");
		treeDto2.setName("dd");
		treeDto2.setOpen(true);
		treeDto2.setChecked(true);
		TreeDto treeDto3 = new TreeDto();
		treeDto3.setId("3");
		treeDto3.setName("dd");
		treeDto3.setOpen(true);
		List<TreeDto> list = new ArrayList<TreeDto>();
		list.add(treeDto2);
		list.add(treeDto3);
		treeDto.setChildren(list);
		toJson(treeDto, response);
	}

	@RequestMapping(value = "/restful", method = RequestMethod.POST)
	public void restful(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// request.getSession().getServletContext();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		// 将资料解码
		String reqBody = sb.toString();
		@SuppressWarnings("unused")
		String udStr = URLDecoder.decode(reqBody, "UTF-8");

		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "你好啊");
		map.put("password", "222");
		// map.put("id",id);
		// JSONObject jsonObject = JSONObject.fromObject(id);

		toJson(map, response);

	}

	@RequestMapping("/treegridInit")
	public void treegridInit(HttpServletRequest request, HttpServletResponse response) {
		TreeGridDto treeGridDto = new TreeGridDto();
		fillRequestDto(request, treeGridDto);
		treeGridDto.setTotal(1);
		TreeGridDto treeGridDto2 = new TreeGridDto();
		treeGridDto2.setId("1");
		treeGridDto2.setBegin("2015-01-01");
		// treeGridDto2.setEnd("2015-01-05");
		treeGridDto2.setName("aaaaa");
		treeGridDto2.setPersons("aaaa");
		treeGridDto2.setState("closed");
		TreeGridDto treeGridDto3 = new TreeGridDto();
		treeGridDto3.setId("2");
		treeGridDto3.setBegin("2015-01-01");
		// treeGridDto3.setEnd("2015-01-05");
		treeGridDto3.setName("bbbb");
		treeGridDto3.setPersons("bbbb");
		treeGridDto3.setState("closed");
		List<TreeGridDto> list = new ArrayList<TreeGridDto>();
		list.add(treeGridDto2);
		list.add(treeGridDto3);
		treeGridDto.setRows(list);
		toJson(treeGridDto, response);
	}

	@RequestMapping("/getsubTree/{id}")
	public void getsubTree(@PathVariable(value = "id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		String pid = id;
		TreeGridDto treeGridDto2 = new TreeGridDto();
		treeGridDto2.setId("11" + Math.random());
		treeGridDto2.setPid(pid);
		treeGridDto2.setBegin("2015-01-01");
		// treeGridDto2.setEnd("2015-01-05");
		treeGridDto2.setName("bbbb");
		treeGridDto2.setPersons("bbbb");
		treeGridDto2.setState("closed");
		List<TreeGridDto> list = new ArrayList<TreeGridDto>();
		list.add(treeGridDto2);
		toJson(list, response);
	}

	/*public static void main(String[] args) throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "111");
		map.put("password", "222");
		map.put("id", "你好");
		JSONObject jsonObject = JSONObject.fromObject(map);

		// NameValuePair aa = new NameValuePair(jsonObject);
		String abc = jsonObject.toString();
		String ustr1 = URLEncoder.encode(abc, "UTF-8");
		String ustr2 = URLDecoder.decode(abc, "UTF-8");
		String ustr3 = URLDecoder.decode(ustr1, "UTF-8");
		System.out.println(abc);
		System.out.println(ustr1);
		System.out.println(ustr2);
		System.out.println(ustr3);
	}*/

	@RequestMapping("/appuploadfile")
	public void appuploadfile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> map1 = new HashMap<String, MultipartFile>();
		// 获取客户端传过来的文件
		map1 = multipartRequest.getFileMap();
		String aaa = multipartRequest.getParameter("paramsJson");
		String udStr = URLDecoder.decode(aaa, "UTF-8");

		@SuppressWarnings({ "rawtypes", "unused" })
		Map returnMap = parserToMap(udStr);

		@SuppressWarnings("unused")
		HashMap<String, String> aaaMap = toHashMap(udStr);

		Iterator<Map.Entry<String, MultipartFile>> entries = map1.entrySet().iterator();
		MultipartFile multipartFile = null;
		MessageDto messageDto = new MessageDto();
		ArrayList<String> errList = new ArrayList<String>();
		// 遍历文件将文件上传
		while (entries.hasNext()) {

			Map.Entry<String, MultipartFile> entry = entries.next();
			multipartFile = entry.getValue();
			String realPath = MessageUtil.$KEY("savePicUrl");
			// 上传文件到指定文件夹
			String message = FileUpLoadDownload.uploadSingleFile(multipartFile, request, response,
					realPath, null);
			// 获取文件流
			@SuppressWarnings("unused")
			InputStream inputStream = FileUpLoadDownload.getFileStream(multipartFile);
			errList.add(message);
		}
		messageDto.setErrList(errList);
		messageDto.setErrType("info");
		JSONObject object = JSONObject.fromObject(messageDto);
		object.put("status", "1");
		toJson(object, response);
	}

	@RequestMapping("/demopop")
	public ModelAndView demopop() {
		ModelAndView result = null;
		result = new ModelAndView("demo/demopop");
		return result;
	}

	/**
	 * json字符串转map
	 * @param s
	 * @return
	 * @date 2016年6月15日 上午10:39:14
	 * @writer wjw.happy.love@163.com
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map parserToMap(String s) {
		Map map = new HashMap();
		JSONObject json = JSONObject.fromObject(s);
		Iterator keys = json.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = json.get(key).toString();
			if (value.startsWith("{") && value.endsWith("}")) {
				map.put(key, parserToMap(value));
			} else {
				map.put(key, value);
			}

		}
		return map;
	}

	/**
	 * 将json格式的字符串解析成Map对象
	 * <li>json格式：{"name":"admin","retries":"3fff","testname"
	 * :"ddd","testretries":"fffffffff"}
	 */
	private static HashMap<String, String> toHashMap(Object object) {
		HashMap<String, String> data = new HashMap<String, String>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		@SuppressWarnings("rawtypes")
		Iterator it = jsonObject.keys();

		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			// String key = String.valueOf(it.next());
			// String value = (String) jsonObject.get(key);
			// data.put(key, value);
		}
		return data;
	}

	/**
	 * 发送邮件和验证码生成的demo
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年6月15日 上午10:39:29
	 * @writer wjw.happy.love@163.com
	 */
	@RequestMapping("/sendmail")
	public void sendmail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		getKaptchaImage(request, response);
		// sendTextEmail("caiyang90@163.com", "测试", "测试",
		// "liuyufeng@nfs-qd.com");

	}

	@RequestMapping("/createDatabase")
	public void createDatabase(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 创建一个新的schema
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql", "CREATE DATABASE chinaws_kaifa5");
		demoService.createDatabase(map);
		// 执行sql脚本（脚本的位置在工程目录resources/chinaws.sql）
		DbUtil.runSqlScript("chinaws_kaifa5");
	}

	// 验证码生成
	private void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
	}

	// 生成页面显示用验证码
	@RequestMapping("/getKaptchaImage")
	public ModelAndView getKaptchaImage1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		// String code =
		// (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

		response.setDateHeader("Expires", 0);

		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");

		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		// return a jpeg
		response.setContentType("image/jpeg");

		// create the text for the image
		String capText = captchaProducer.createText();

		// store the text in the session
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

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

	@RequestMapping("/selectChange")
	public void selectChange(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ComboxDto DTO = new ComboxDto();
		ComboxDto comboxDto = new ComboxDto();
		comboxDto.setCode("3");
		comboxDto.setName("岗位3");
		ComboxDto comboxDto2 = new ComboxDto();
		comboxDto2.setCode("4");
		comboxDto2.setName("岗位4");
		List<ComboxDto> list = new ArrayList<ComboxDto>();
		list.add(comboxDto);
		list.add(comboxDto2);
		DTO.setRows(list);
		toJson(DTO, response);
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MessageDto messageDto = new MessageDto();
		// 返回消息 start
		ArrayList<String> errList = new ArrayList<String>();
		errList.add(MessageUtil.$VALUE("export_success"));
		messageDto.setErrList(errList);
		messageDto.setErrType("info");
		// 返回消息 endlist
		toJson(messageDto, response);
	}

	@RequestMapping("/getEditData")
	public void getEditData(HttpServletRequest request, HttpServletResponse response) {
		ComboxDto comboxDto = new ComboxDto();
		comboxDto.setCode("1");
		comboxDto.setName("岗位1");
		// 返回消息 end
		toJson(comboxDto, response);
	}

	// 一级树结构数据初始化
	@RequestMapping("/initAjaxTree")
	public void initAjaxTree(HttpServletRequest request, HttpServletResponse response) {
		TreeDto treeDto = new TreeDto();
		treeDto.setId("1");
		treeDto.setpId("0");
		treeDto.setIsParent("true");// 设置成true表示是父节点
		treeDto.setName("bb");
		// treeDto.setOpen(false);
		// treeDto.setChecked(true);
		toJson(treeDto, response);
	}

	// 树结构数据初始化
	@RequestMapping("/getAjaxNode")
	public void getAjaxNode(HttpServletRequest request, HttpServletResponse response) {

		TreeDto treeDto = new TreeDto();
		// 参数映射到dto
		fillRequestDto(request, treeDto);
		treeDto.setId("2");
		treeDto.setName("bb");
		treeDto.setOpen(false);
		toJson(treeDto, response);
	}

}
