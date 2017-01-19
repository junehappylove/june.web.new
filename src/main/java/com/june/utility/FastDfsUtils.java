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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.june.common.Constants;
import com.june.utility.exception.CustomException;
import com.june.utility.exception.FastDFSException;

/**
 * 
 * FastDFS文件上传下载工具类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月10日 下午4:47:03
 */
public class FastDfsUtils {

	/**
	 * 根据路径获取文件
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * @date 2016年6月15日 上午10:42:06
	 * @writer wjw.happy.love@163.com
	 */
	public static byte[] getFile(String url) throws Exception {
		if (url.contains("-")) {
			url = url.replaceAll("-", "/");
		}
		if (url.contains("/") && !url.contains(":")) {
			url = url.replaceFirst("/", ":");
		}
		String groupName = "";
		String fileName = "";
		if (url.contains(":")) {
			groupName = url.split(":")[0];
			fileName = url.split(":")[1];
		} else {
			throw new FastDFSException(Constants.REMOTE_ADDRESS_ERROR);
		}

		String classPath = new File(FastDfsUtils.class.getResource("/").getFile()).getCanonicalPath();
		// TODO 如果不成功，将配置文件名修改为fdfs_client.conf,相应修改资源目录下的文件名
		String configFilePath = classPath + File.separator + "conf" + File.separator + "fdfs_client.properties";
		ClientGlobal.init(configFilePath);
		TrackerServer trackerServer = null;
		TrackerClient trackerClient = new TrackerClient();
		try {
			trackerServer = trackerClient.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 如果trackerserver为null，则抛出异常
		if (trackerServer == null) {
			throw new FastDFSException(Constants.NO_AVALIABLE_TRACKER);
		}
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);

		String group_name = groupName;
		String remote_filename = fileName;
		// FileInfo fi = storageClient.get_file_info(group_name,
		// remote_filename);
		byte[] buffer = storageClient.download_file(group_name, remote_filename);
		if (buffer == null) {
			throw new FastDFSException(Constants.REMOTE_FILE_NO_EXIST);
		}
		return buffer;
	}

	/**
	 * 上传文件
	 * 
	 * @param inputstream
	 *            上传的文件流
	 * @param uploadFileName
	 *            上传的文件名字
	 * @param author
	 *            上传人
	 * @return String 存储路径形式:group:地址 文件存放的group和路径
	 * @throws Exception
	 * @date 2016年6月27日 下午1:54:06
	 * @writer wjw.happy.love@163.com
	 */
	public static String uploadFile(InputStream inputstream, String uploadFileName, String author) throws Exception {
		String fileExtName = "";
		if (uploadFileName.contains(".")) {
			// 获取文件后缀
			fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		} else {
			throw new CustomException("文件无扩展名，请检查上传的文件！");
		}
		// 读取fdfs的配置文件
		String classPath = new File(FastDfsUtils.class.getResource("/").getFile()).getCanonicalPath();
		// TODO 如果不成功，将配置文件名修改为fdfs_client.conf,相应修改资源目录下的文件名
		String configFilePath = classPath + File.separator + "conf" + File.separator + "fdfs_client.properties";

		ClientGlobal.init(configFilePath);
		// 连接服务器
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 如果trackerserver为null，则抛出异常
		if (trackerServer == null) {
			throw new FastDFSException(Constants.NO_AVALIABLE_TRACKER);
		}
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("fileName", uploadFileName);
		meta_list[1] = new NameValuePair("fileExtName", fileExtName);
		meta_list[2] = new NameValuePair("author", author);
		FileInputStream fis = (FileInputStream) inputstream;
		byte[] file_buff = null;
		if (fis != null) {
			int len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}

		String group_name = null;
		StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
		if (storageServers == null) {
			System.err.println("get store storage servers fail, error code: " + storageClient.getErrorCode());
		}

		String[] results = storageClient.upload_file(file_buff, fileExtName, meta_list);

		if (results == null) {
			System.err.println("upload file fail, error code: " + storageClient.getErrorCode());
			return null;
		}
		trackerServer.close();
		group_name = results[0];
		String remote_filename = results[1];

		/*
		 * ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer,
		 * group_name, remote_filename); if (servers == null){
		 * System.err.println("get storage servers fail, error code: " +
		 * trackerClient.getErrorCode()); }
		 */
		// System.out.print(group_name + ":" + remote_filename);
		// 返回存储的group和服务器上的文件名
		// remote_filename = remote_filename.replaceAll("/", "-");
		return group_name + ":" + remote_filename;
	}

	/**
	 * ckeditor上传文件用
	 * 
	 * @param inputstream
	 *            上传的文件流
	 * @param uploadFileName
	 *            上传的文件名字
	 * @param author
	 *            上传人
	 * @return String 存储路径形式:group:地址
	 * @throws Exception
	 * @date 2016年6月27日 下午1:55:25
	 * @writer wjw.happy.love@163.com
	 */
	public static String uploadCkeditorFile(InputStream inputstream, String uploadFileName, String author)
			throws Exception {
		String fileExtName = "";
		if (uploadFileName.contains(".")) {
			// 获取文件后缀
			fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		} else {
			return null;
		}
		// 读取fdfs的配置文件
		String classPath = new File(FastDfsUtils.class.getResource("/").getFile()).getCanonicalPath();
		// TODO 如果不成功，将配置文件名修改为fdfs_client.conf,相应修改资源目录下的文件名
		String configFilePath = classPath + File.separator + "conf" + File.separator + "fdfs_client.properties";

		ClientGlobal.init(configFilePath);
		// 连接服务器
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 如果trackerserver为null，则抛出异常
		if (trackerServer == null) {
			throw new FastDFSException(Constants.NO_AVALIABLE_TRACKER);
		}
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("fileName", uploadFileName);
		meta_list[1] = new NameValuePair("fileExtName", fileExtName);
		meta_list[2] = new NameValuePair("author", author);
		FileInputStream fis = (FileInputStream) inputstream;
		byte[] file_buff = null;
		if (fis != null) {
			int len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}

		String group_name = null;
		StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
		if (storageServers == null) {
			System.err.println("get store storage servers fail, error code: " + storageClient.getErrorCode());
		}

		String[] results = storageClient.upload_file(file_buff, fileExtName, meta_list);

		if (results == null) {
			System.err.println("upload file fail, error code: " + storageClient.getErrorCode());
			return null;
		}
		trackerServer.close();
		group_name = results[0];
		String remote_filename = results[1];

		/*
		 * ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer,
		 * group_name, remote_filename); if (servers == null){
		 * System.err.println("get storage servers fail, error code: " +
		 * trackerClient.getErrorCode()); }
		 */
		// System.out.print(group_name + ":" + remote_filename);
		// 返回存储的group和服务器上的文件名
		remote_filename = remote_filename.replaceAll("/", "-");
		return group_name + "-" + remote_filename;
	}
}
