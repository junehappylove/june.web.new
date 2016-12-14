/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.errorCode.ErrorCodeDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.errorCode;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.PageDTO;
import com.june.dto.back.bussiness.vehicle.VehicleDto;

/**
 * 故障代码管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class ErrorCodeDto extends PageDTO<ErrorCodeDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;
	@Size(min = 0,max = 255,message="{inputlength_error};操作指南V2;255")
	private String guidV2;// 司机停止简要操作指南
	@Size(min = 0,max = 255,message="{inputlength_error};操作指南V1;255")
	private String guidV1;// 司机停止简要操作指南
	@Size(min = 0,max = 255,message="{inputlength_error};操作指南V0;255")
	private String guidV0;// 司机停止简要操作指南
	@Size(min = 0,max = 32,message="{inputlength_error};子系统;32")
	private String subSystem;// 子系统
	@Size(min = 0,max = 255,message="{inputlength_error};故障内容;255")
	private String errorReason;// 故障原因
	@Size(min = 0,max = 255,message="{inputlength_error};故障内容;255")
	private String errorDesc;// 故障描述
	@NotNull(message="{mustinput_error};故障等级")
	@NotEmpty(message="{mustinput_error};故障等级")
	private String errorLevel; // 故障代码名称
	@NotNull(message="{mustinput_error};故障代码")
	@NotEmpty(message="{mustinput_error};故障代码")
	@Size(min = 4,max = 16,message="{inputlength_range_error};故障代码;4;16")
	private String errorCode; // 故障代码
	private String errorId;//故障代码主键ID
	private String impactVehicle;//影响车型ID
	private VehicleDto vehicleDto;//故障代码所影响的车型
	private String vehicle;//影响车辆		20160613 新添加字段
	private String port;//端口号
	private String wordOffset;//字节偏移
	private String byteOffset;//位偏移
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getWordOffset() {
		return wordOffset;
	}

	public void setWordOffset(String wordOffset) {
		this.wordOffset = wordOffset;
	}

	public String getByteOffset() {
		if(byteOffset!=null){
			byteOffset = byteOffset.replace("bit", "");
			byteOffset = byteOffset.replace("BIT", "");
			byteOffset = byteOffset.replace("Bit", "");
		}
		return byteOffset;
	}

	public void setByteOffset(String byteOffset) {
		if(byteOffset!=null){
			byteOffset = byteOffset.replace("bit", "");
			byteOffset = byteOffset.replace("BIT", "");
			byteOffset = byteOffset.replace("Bit", "");
		}
		this.byteOffset = byteOffset;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getGuidV2() {
		return guidV2;
	}

	public void setGuidV2(String guidV2) {
		this.guidV2 = guidV2;
	}

	public String getGuidV1() {
		return guidV1;
	}

	public void setGuidV1(String guidV1) {
		this.guidV1 = guidV1;
	}

	public String getGuidV0() {
		return guidV0;
	}

	public void setGuidV0(String guidV0) {
		this.guidV0 = guidV0;
	}
	
	public String getSubSystem() {
		subSystem = subSystem.trim();
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		subSystem = subSystem.trim();
		this.subSystem = subSystem;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}

	public String getErrorCode() {
		errorCode = errorCode.trim();
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		errorCode = errorCode.trim();
		this.errorCode = errorCode;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getImpactVehicle() {
		return impactVehicle;
	}

	public void setImpactVehicle(String impactVehicle) {
		this.impactVehicle = impactVehicle;
	}

	public VehicleDto getVehicleDto() {
		return vehicleDto;
	}

	public void setVehicleDto(VehicleDto vehicleDto) {
		this.vehicleDto = vehicleDto;
	}
	public ErrorCodeDto(String errorId) {
		super();
		this.errorId = errorId;
	}
	public ErrorCodeDto() {
		super();
	}

	@Override
	protected String getDtoName() {
		return "故障代码";
	}
}
