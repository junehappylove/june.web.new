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

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {

	public static void creteHtml(String ftl, String htmlName, Map<String, Object> map, String realPath,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TemplateException {
		Configuration config = new Configuration();
		// 设置要解析的模板所在的目录，并加载模板文件
		try {
			config.setServletContextForTemplateLoading(httpServletRequest.getServletContext(), "/");
			config.setEncoding(Locale.getDefault(), "utf-8");

			map.put("C", httpServletRequest.getContextPath());
			Template template = config.getTemplate(ftl);
			template.setEncoding("utf-8");
			// 合并数据模型与模板
			String path = httpServletRequest.getServletContext().getRealPath("/");
			File file = new File(path + realPath.split("/")[0]);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			file = new File(path + realPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			File fileName = new File(path + realPath + htmlName);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
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
			HttpServletRequest httpServletRequest) {
		Configuration config = new Configuration();
		StringWriter out = new StringWriter();
		try {
			config.setServletContextForTemplateLoading(httpServletRequest.getServletContext(), "/");
			config.setEncoding(Locale.getDefault(), "utf-8");

			root.put("C", httpServletRequest.getContextPath());
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
