/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.vehicle.VehicleDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.vehicle;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.PageDTO;
import com.june.dto.back.system.base.UserInfoDto;

/**
 * 车型管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class VehicleDto extends PageDTO<VehicleDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;

	@Size(min = 0,max = 255,message="{inputlength_error};车型描述;255")
	private String vehicleDesc;	// 车型描述
	@NotNull(message="{mustinput_error};车型名称")
	@NotEmpty(message="{mustinput_error};车型名称")
	@Size(min = 0,max = 32,message="{inputlength_range_error};车型名称;0;32")
	private String vehicleName; // 车型名称，按照车型名称建立对应的ftp目录即可
	private String ftpPath;		//车型FTP地址
	private String vehicleId; 	// 车型ID
	private List<UserInfoDto> users;//车型用户管理关系

	/**
	 * @param vehicleId
	 */
	public VehicleDto(String vehicleId) {
		super();
		this.vehicleId = vehicleId;
	}

	/**
	 * 
	 */
	public VehicleDto() {
		super();
	}

	public List<UserInfoDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfoDto> users) {
		this.users = users;
	}

	public String getVehicleDesc() {
		return vehicleDesc;
	}

	public void setVehicleDesc(String vehicleDesc) {
		this.vehicleDesc = vehicleDesc;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getFtpPath() {
		ftpPath = StringUtils.isEmpty(ftpPath) ? "/" + this.vehicleName : this.ftpPath;
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	@Override
	protected String getDtoName() {
		return "车型";
	}

}
