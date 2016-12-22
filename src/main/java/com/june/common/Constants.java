/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.common;

import com.june.utility.MessageUtil;

/**
 * 
 * 系统常量类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月8日 下午4:36:33
 */
public class Constants {
	/** 项目平台环境：dev,test,pro */
	public final static String ENVIRONMENT = MessageUtil.$KEY("project.environment");
	public final static String ERROR_CODE_WRONG = MessageUtil.$KEY("error_code_wrong");
	public final static String ERROR_CODE_EMPTY = MessageUtil.$KEY("error_code_empty");
	public final static String USER_NOT_EXIT = MessageUtil.$KEY("user_not_exist");
	public final static String USER_LOCKED = MessageUtil.$KEY("user_locked");
	public final static String SYS_ERROR = MessageUtil.$KEY("sys_error");
	public final static String NO_AVALIABLE_TRACKER = MessageUtil.$KEY("no_avaliable_tracker");
	public final static String REMOTE_ADDRESS_ERROR = MessageUtil.$KEY("remotefile_address_error");
	public final static String REMOTE_FILE_NO_EXIST = MessageUtil.$KEY("remotefile_not_exist");
	public final static String USER_HEAD = MessageUtil.$KEY("user.head");
	public final static String USER_HEAD_PATH = "/upload/";//用户头像保存目录
	public final static String USER_HEAD_TYPE = ".jpg";//用户头像保存格式类型
	
	/** 空 "" */
	public final static String EMPTY = "";
	public final static int COMMIT_NUM = 1000;//最多一次性提交1000条
	
	/** 删除标志 */
	public final static String DEL_SIGN_NODEL = "0";// 未删除
	public final static String DEL_SIGN_DEL = "1";// 已删除

	/** 行政区划级别  */
	public final static String DISTRICT_LEVEL_SHENG = "2";// 省级
	public final static String DISTRICT_LEVEL_SHI = "3";// 市
	public final static String DISTRICT_LEVEL_XIAN = "4";// 区、县
	public final static String DISTRICT_LEVEL_ZHEN = "5";// 镇


	/** 图片上传判断 */
	public final static String UPLOAD_SUCC = MessageUtil.$KEY("upload_success");
	public final static String UPLOAD_FAIL = MessageUtil.$KEY("upload_fail");

	/** 用户 */
	public final static String DEFAULT_PASSWORD = "111111";  //新增用户默认密码
	public final static String FLAG_YES = "Y";  //是
	public final static String FLAG_NO = "N";   //否
	public final static String FLAG_YES_1 = "1";  //是
	public final static String FLAG_NO_0 = "0";   //否
	public final static String FLAG_TRUE = "T";  //是
	public final static String FLAG_FALSE = "F";   //否
	
	/** properties文件加密密钥 --请尊重原作者的标识  */
	public static final String KEY_STRING = "june_web_new@junehappylove";//生成密钥的字符串  
	public static final byte[] KEY_BYTE = { 106, 117, 110, 101, 95, 119, 101, 98, 95, 110, 101, 119, 64, 106, 117, 110,
			101, 104, 97, 112, 112, 121, 108, 111, 118, 101 };
	
	
	/** per_event表，用户越界事件 */
	public static final String PERSON_EVENT_OUTOFBOUNDS = MessageUtil.$KEY("person_event_out");  //用户出界报警提示信息
	public static final String PERSON_EVENT_OUTOFBOUNDSTYPE = "05";  //出界的事件类型，与sys_code表中关联
	
	/** 用户用邮箱注册时，给用户发送邮件的标题 */
	public static final String REGISTER_EMAIL_SUBJECT = MessageUtil.$KEY("register_email_subject");
	public static final String REGISTER_EMAIL_CODE = MessageUtil.$KEY("register_email_code");
	public static final String EMAIL_ERROR = MessageUtil.$KEY("email_error");
	public static final String FINDPASSWORD_EMAIL_SUBJECT = MessageUtil.$KEY("find_password_email_subject");
	public static final String RESETPASSWORD_ERROR = MessageUtil.$KEY("reset_password_error");
	
	/**  操作类型，用于操作日志时存储  Add 添加操作 */
	public static final String OPERATE_TYPE_ADD = "Add";
	/**  操作类型，用于操作日志时存储  Update 修改操作 */
	public static final String OPERATE_TYPE_UPDATE = "Update";
	/**  操作类型，用于操作日志时存储  View 查看操作 */
	public static final String OPERATE_TYPE_VIEW = "View";
	/**  操作类型，用于操作日志时存储  Delete 删除操作 */
	public static final String OPERATE_TYPE_DELETE = "Delete";
	/**  操作类型，用于操作日志时存储  Search 查询操作 */
	public static final String OPERATE_TYPE_SEARCH = "Search";
	/**  操作类型，用于操作日志时存储  Login 登录操作 */
	public static final String OPERATE_TYPE_LOGIN = "Login";
	/**  操作类型，用于操作日志时存储  Init 初始化操作 */
	public static final String OPERATE_TYPE_INIT = "Init";
	/**  操作类型，用于操作日志时存储  Other 其他操作 */
	public static final String OPERATE_TYPE_OTHER = "Other";
	
	/**  是否记录日志操作 */
	public static final boolean IF_LOG = Boolean.parseBoolean(MessageUtil.$KEY("log"));//21;
	
	/**
	 * 消息类型
	 */
	public static final String MESSAGE_TYPE_ERROR = "error";
	public static final String MESSAGE_TYPE_INFO = "info";
	public static final String MESSAGE_TYPE_WARN = "warning";
	
	/**
	 * url访问权限
	 */
	public static final String AUTHORITY = "access";
	
	/**
	 * 允许连续输入密码错误的次数
	 */
	public static final int ATTEMP_TIME = 3;
	
	/**
	 * 超过输入次数锁定的时间(单位：分)
	 */
	public static final int LOCK_TIME = 5;
	
	/**
	 * 用户默认密码：111111
	 */
	public static final String USER_DEFAULT_PASSWORD = "9db06bcff9248837f86d1a6bcf41c9e7";

	/**
	 * 默认取30条数据
	 */
	public static final int DEFAULT_ROW_DATA = 30;

	public static final String FTP_ROOT_DIR = MessageUtil.$KEY("ftp.host");//"192.168.100.5";
	public static final String FTP_USER = MessageUtil.$KEY("ftp.user");//"nfschina";
	public static final String FTP_PASSWORD = MessageUtil.$KEY("ftp.password");//"nfschina";
	public static final String FTP_LOCATION = MessageUtil.$KEY("ftp.location");//"FTP";
	public static final int FTP_PORT = Integer.parseInt(MessageUtil.$KEY("ftp.port"));//21;
	
	public static final String FTP_ROOT_DIR_ = "192.168.100.5";
	public static final String FTP_USER_ = "nfschina";
	public static final String FTP_PASSWORD_ = "nfschina";
	public static final String FTP_LOCATION_ = "FTP";
	public static final int FTP_PORT_ = 21;
	
	/**
	 * 设置默认的编码格式：GBK
	 */
	public static final String DEFAULT_ENCODE = "GBK";

	/*
	 * 定义FTP目录
	 */
	/**  FTP目录，基本操作 */
	public static final String DIRECTORY_N = "基本操作";
	/**  FTP目录，应急操作*/
	public static final String DIRECTORY_Y = "应急操作";
	/**  FTP目录，参考文件 */
	public static final String DIRECTORY_R = "参考文件";
	/**  FTP目录，数据解析 */
	public static final String DIRECTORY_D = "数据解析";
	/**  FTP目录，审批目录 */
	public static final String DIRECTORY_S = "审批目录";
	/**  FTP目录，客户端版本 */
	public static final String DIRECTORY_C = "客户端版本";
	/**  FTP目录，文件 */
	public static final String DIRECTORY_F = "文件";
	/**  FTP目录，视频 */
	public static final String DIRECTORY_V = "视频";
	/**  FTP目录，其他 */
	public static final String DIRECTORY_O = "其他";
	/**  FTP目录，根目录:/ */
	public static final String DIRECTORY_ROOT = "/";
	
	/** 本地保存文件路径 */
	public static final String DIRECTORY_LOCAL_DOWNLOAD_ = "C:/MSOCache/temp/";
	/** 本地下载文件路径 */
	public static final String DIRECTORY_LOCAL_DOWNLOAD = MessageUtil.$KEY("file.downloadpath");

	/**
	 * 目录集合
	 */
	public static final String[] DIRECTORYS = {DIRECTORY_N, DIRECTORY_Y, DIRECTORY_R, DIRECTORY_D};//去掉审批目录
	public static final String[] DIRECTORYS_2 = {DIRECTORY_F, DIRECTORY_V};
	
	/**
	 * 一次向数据库中提交数据的最大数量
	 */
	public static final int DATA_MAX = 1000;

	/** 存放步骤文件名 */
	public static final String FILE_STEP = "step.xml";
	/** 图片解析的步骤div的id */
	public static final String DIV_STEP = "step";


}
