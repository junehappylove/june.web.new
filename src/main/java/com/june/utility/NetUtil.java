/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.utility.NetUtil.java
 * 日期:2017年1月19日
 */
package com.june.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * 网络操作工具类： NetUtil <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午9:29:41
 * @version 1.0.0
 */
public class NetUtil {

	final static String UTF8 = "utf-8";
	final static String GBK = "gbk";
	private final static String NEW_LINE = "\n";
	private final static int TIME_OUT = 5000;// 链接超时时间
	private final static int TRY_TIMES = 5;// 链接超时时间

	/**
	 * 回调api直接反转成java对象返回 <br>
	 * <b>注意：</b> 调用api必须是返回的json数据
	 * 
	 * @param api
	 *            请求的api
	 * @param clazz
	 *            T
	 * @return List&lt;T>
	 */
	public static <T> List<T> callApi(String api, Class<T> clazz) {
		return JsonUtil.toList(callApi(api), clazz);
	}

	/**
	 * 调用API
	 * 
	 * @param api
	 *            请求的api
	 * @return 调用api返回的字符串(包含json字符串)数据
	 */
	public static String callApi(String api) {
		return getContentFormURL(api);
	}

	/**
	 * 访问url获取结果数据，默认以utf-8字符格式返回
	 * 
	 * @param url
	 *            请求的url地址
	 * @see #getContentFormURL(java.lang.String,java.lang.String)
	 * @return 字符串结果数据
	 */
	public static String getContentFormURL(String url) {
		return getContentFormURL(url, UTF8);
	}

	/**
	 * 访问url获取结果数据
	 *
	 * @param url
	 *            url中如果包含中文等必须是经过编码后的地址
	 * @param code
	 *            指定返回结果内容编码，如果不指定默认为UTF-8
	 * @return 字符串结果数据
	 */
	public static String getContentFormURL(String url, String code) {
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			if (url != null) {
				sb = new StringBuilder();
				// 将url加码
				conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setConnectTimeout(TIME_OUT);
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), code == null ? UTF8 : code));// UTF-8
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sb.append(temp).append(NEW_LINE);
				}
			} else {
				sb = null;
			}
		} catch (IOException exe) {
			exe.printStackTrace();
			return null;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 判断地址是否链接成功
	 * 
	 * @param urlstr
	 *            地址
	 * @return 成功true，否则false
	 */
	public static boolean canConnect(String urlstr) {
		URL url = checkURL(urlstr);
		if (url == null) {
			return false;
		}
		return true;
	}

	/**
	 * 判断url是否可达
	 * 
	 * @param urlstr
	 * @return 可达返回URL，否则返回null
	 */
	public static synchronized URL checkURL(String urlstr) {
		URL url = null;
		HttpURLConnection con = null;
		int state;
		int counts = 0;
		if (urlstr == null || urlstr.length() <= 0) {
			return null;
		}
		while (counts < TRY_TIMES) {
			try {
				url = new URL(urlstr);
				con = (HttpURLConnection) url.openConnection();
				state = con.getResponseCode();
				if (state == 200) {
					break;
				}
			} catch (Exception ex) {
				counts++;
				url = null;
				continue;
			}
		}
		return url;
	}

	/**
	 * URL地址中的中文编码
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 * @date 2017年1月19日 下午11:30:23
	 * @writer junehappylove
	 */
	public static String $(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
			content = content.replaceAll("\\+", "%20"); // url中将空格转换成%20
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}
}
