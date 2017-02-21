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

import com.june.util.Const;
import com.june.util.MessageUtil;

/**
 * 
 * 系统常量类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月8日 下午4:36:33
 */
public class Constants extends Const{
	
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

	/** 存放步骤文件名 */
	public static final String FILE_STEP = "step.xml";
	/** 图片解析的步骤div的id */
	public static final String DIV_STEP = "step";


}
