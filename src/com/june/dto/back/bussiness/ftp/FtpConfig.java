/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.ftp.FtpConfig.java
 * 日期:2016年6月14日
 */
package com.june.dto.back.bussiness.ftp;

import com.june.common.Constants;

/**
 * TODO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月14日 上午11:15:59
 */
public final class FtpConfig extends FtpDto {
	private static final long serialVersionUID = 4619413766105329607L;
	
	private static FtpConfig ins = new FtpConfig();
	
	public static final FtpConfig get(){
		ins.setFtpIP(Constants.FTP_ROOT_DIR);
		ins.setPort(Constants.FTP_PORT);
		ins.setFtpUser(Constants.FTP_USER);
		ins.setFtpPassword(Constants.FTP_PASSWORD);
		ins.setFtpPath(Constants.FTP_LOCATION);
		return ins;
	}
}
