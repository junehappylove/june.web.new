/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.errorCode.ErrorCode.java
 * 日期:2016年7月11日
 */
package com.june.dto.back.bussiness.errorCode;

/**
 * TODO <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年7月11日 下午3:08:04
 */
public class ErrorCode {
	private String errorId;
	private String errorCode;
	private String vehicleId;

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * @param errorId
	 * @param errorCode
	 */
	public ErrorCode(String errorId, String errorCode) {
		super();
		this.errorId = errorId;
		this.errorCode = errorCode;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 */
	public ErrorCode() {
		// TODO Auto-generated constructor stub
	}

}
