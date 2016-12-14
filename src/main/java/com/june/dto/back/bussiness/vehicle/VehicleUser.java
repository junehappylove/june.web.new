/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.vehicle.VehicleUser.java
 * 日期:2016年5月10日
 */
package com.june.dto.back.bussiness.vehicle;

import com.june.common.PageDTO;

/**
 * 车型人员关系 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月10日 下午12:38:20
 */
public class VehicleUser extends PageDTO<VehicleUser>{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 2273770542996852390L;
	private String vehicleId;
	private String userId;
	
	private String vehicleName;
	private String userName;
	
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @param vehicleId
	 * @param userId
	 */
	public VehicleUser(String vehicleId, String userId) {
		super();
		this.vehicleId = vehicleId;
		this.userId = userId;
	}
	/**
	 * @param vehicleId
	 */
	public VehicleUser(String vehicleId) {
		super();
		this.vehicleId = vehicleId;
	}
	/**
	 * 
	 */
	public VehicleUser() {
		super();
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	protected String getDtoName() {
		return "车型人员";
	}
}
