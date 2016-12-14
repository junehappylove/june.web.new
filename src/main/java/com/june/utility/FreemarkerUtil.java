package com.june.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 模版工具类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月13日 下午2:52:05
 */
public class FreemarkerUtil {
	private static final Logger log = LoggerFactory.getLogger(FreemarkerUtil.class);
	/** FreeMarker配置 */
	private static Configuration config = new Configuration(Configuration.VERSION_2_3_23);
	/** 是否已初始化 */
	private static boolean isInit = false;
	/** 应用所在路径 */
	private static String appPath = null;

	private static final String PATH_SEPARATOR = "/";
	private static final String UTF8 = "utf-8";

	/**
	 * 初始化FreeMarker配置
	 * 
	 * @param applicationPath
	 *            应用所在路径
	 * @date 2016年12月13日 下午5:41:34
	 * @writer iscas
	 */
	public static void initFreeMarker(String applicationPath) {
		if (!(isInit)) {
			try {
				appPath = applicationPath;
				// 加载模版
				File file = new File(new StringBuffer(appPath).append(File.separator).toString());
				// 设置要解析的模板所在的目录，并加载模板文件
				config.setDirectoryForTemplateLoading(file);
				// 设置文件编码为UTF-8
				config.setEncoding(Locale.getDefault(), UTF8);
				isInit = true;
			} catch (IOException e) {
				log.error("初始化FreeMarker配置出错", e);
			}
		}
	}

	/**
	 * 跟据数据及模板生成文件
	 * 
	 * @param data
	 *            一个Map的数据结果集
	 * @param templateFileName
	 *            ftl模版路径(已默认为WEB-INF/templates,文件名相对此路径)
	 * @param outFileName
	 *            生成文件名称(可带路径)
	 * @param isAbsPath
	 *            是否绝对路径
	 * @date 2016年12月13日 下午5:41:40
	 * @writer iscas
	 */
	public static void crateFile(Map<String, Object> data, String templateFileName, String outFileName,
			boolean isAbsPath) {
		if (!isInit) {
			System.out.println("FreeMarker模板引擎未初始化,请确认已经调用initFreeMarker()方法对其进行了初始化");
		}
		Writer out = null;
		try {
			// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			Template template = config.getTemplate(templateFileName, UTF8);
			// template.setEncoding(ENCODING);

			// 生成文件路径
			// 如果是绝对路径则直接使用
			if (isAbsPath) {
				outFileName = new StringBuffer(outFileName).toString();
			} else {
				// 相对路径则使用默认的appPath加上输入的文件路径
				outFileName = new StringBuffer(appPath).append(File.separator).append(outFileName).toString();
			}
			String outPath = outFileName.substring(0, outFileName.lastIndexOf(PATH_SEPARATOR));
			// 创建文件目录
			FileUtils.forceMkdir(new File(outPath));
			File outFile = new File(outFileName);
			out = new OutputStreamWriter(new FileOutputStream(outFile), UTF8);

			// 处理模版
			template.process(data, out);

			out.flush();
			log.info("由模板文件" + templateFileName + "生成" + outFileName + "成功.");
		} catch (Exception e) {
			log.error("由模板文件" + templateFileName + "生成" + outFileName + "出错.", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				log.error("关闭Write对象出错", e);
			}
		}
	}

	public static void creteHtml(String ftl, String htmlName, Map<String, Object> map, String realPath,
			HttpServletRequest request, HttpServletResponse response) throws TemplateException {
		// Configuration config = new
		// Configuration(Configuration.VERSION_2_3_23);
		// 设置要解析的模板所在的目录，并加载模板文件
		try {
			config.setServletContextForTemplateLoading(request.getServletContext(), PATH_SEPARATOR);
			config.setEncoding(Locale.getDefault(), UTF8);

			map.put("C", request.getContextPath());
			Template template = config.getTemplate(ftl);
			// template.setEncoding("utf-8");//过时
			// 合并数据模型与模板
			String path = request.getServletContext().getRealPath(PATH_SEPARATOR);
			File file = new File(path + realPath.split(PATH_SEPARATOR)[0]);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			file = new File(path + realPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			File fileName = new File(path + realPath + htmlName);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), UTF8));
			template.process(map, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 将解析之后的文件内容返回字符串
	 * 
	 * @param name
	 *            模板文件名
	 * @param root
	 *            数据Map
	 * @return
	 */
	public static String printString(String templatePaht, Map<String, Object> root,
			HttpServletRequest request) {
		// Configuration config = new
		// Configuration(Configuration.VERSION_2_3_23);
		StringWriter out = new StringWriter();
		try {
			config.setServletContextForTemplateLoading(request.getServletContext(), PATH_SEPARATOR);
			config.setEncoding(Locale.getDefault(), UTF8);

			root.put("C", request.getContextPath());
			// 通过一个文件输出流，就可以写到相应的文件中
			Template temp = config.getTemplate(templatePaht);
			temp.process(root, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out.toString();
	}

}
