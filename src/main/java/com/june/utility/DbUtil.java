/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.utility;

import java.sql.DriverManager;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import com.mysql.jdbc.Connection;

/**
 * 
 * DB操作工具类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年8月31日 下午5:51:18
 */
public class DbUtil {

	/**
	 * 执行sql脚本文件
	 * 
	 * @param newSchemaName
	 *            一个新的schema名称
	 * @throws Exception
	 * @date 2016年8月31日 下午5:50:18
	 * @writer wjw.happy.love@163.com
	 */
	public static void runSqlScript(String newSchemaName) throws Exception {
		// 获取jdbc中的数据库url并将url中的schemaname置换成newSchemaName
		Properties props = Resources.getResourceAsProperties("mybatis/jdbc.properties");
		String url = props.getProperty("url");
		String temp = url.split("/")[3];
		String schemaName = temp.split("\\?")[0];
		url = url.replace(schemaName, newSchemaName);
		String driver = props.getProperty("driver");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		Class.forName(driver).newInstance();
		// 创建一个连接
		Connection conn = (Connection) DriverManager.getConnection(url, username, password);
		ScriptRunner runner = new ScriptRunner(conn);
		runner.setErrorLogWriter(null);
		runner.setLogWriter(null);
		runner.runScript(Resources.getResourceAsReader("chinaws.sql"));
	}
}
