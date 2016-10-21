/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.guide.GuideDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.guide;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.june.common.PageDTO;

/**
 * 操作指南审批管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class GuideApproveDto extends PageDTO<GuideApproveDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;
	private String approveId;			// 审批主键ID [GUIDE_APPROVE_ID]
	@NotNull(message="{mustinput_error};指南名称")
	@NotEmpty(message="{mustinput_error};指南名称")
	@Size(min = 0,max = 64,message="{inputlength_range_error};指南名称;0;64")
	private String guideName; 			// 操作指南名称 [GUIDE_NAME]
	@Size(min = 0,max = 255,message="{inputlength_range_error};操作描述;0;255")
	private String guideInfo;			// 操作描述[GUIDE_INFO]
	private String ftpPath;				// 当前目录路径 [DIRECTORY_PATH]
	private String toPath;				// 待存目标路径 [TO_PATH]
	@NotNull(message="{mustinput_error};审批状态")
	@NotEmpty(message="{mustinput_error};审批状态")
	private String approveState; 		// 审批状态 [APPROVE_STATE]0：未审批；1:审批通过；2：审批未通过
	private String approveUserId;		// 审批人ID [APPROVE_USER_ID]
	private Timestamp approveTime;		// 审批时间 [APPROVE_TIME]
	@Size(min = 0,max = 255,message="{inputlength_error};审批记录内容;255")
	private String approveInfo;			// 审批记录内容 [APPROVE_INFO]
	private String vehicleId;			// 所属车型ID [VEHICLE_TYPE]
	private String emergency;			// 是否应急[IS_EMERGENCY]：[1 应急 0 基本]
	private String operateType;			// 操作类型[ADD_UPDATE]：增加操作指南0, 修改存在指南步骤1 增加指南步骤2，增加修改视频3
	private String guideId; 			// 操作指南ID,修改必须要有ID[GUIDE_ID]
	private String xmlStep;				// 第几步 [XML_STEP]
	private String xmlImage;			// 第几步的图片名称（为空不修改） [XML_IMAGE]
	private String xmlText; 			// 第几步的文本描述 [XML_TEXT]
	private String videoName;			// 视频名称 [VIDEO_NAME]
	////////////////////////////////////////////////////////////////////////////
	private String vehicleName;			// 车型名称 
	private String operateDesc;			// 操作描述，对应 operateType
	
	public String getOperateDesc() {
		return operateDesc;
	}
	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getApproveId() {
		return approveId;
	}
	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public String getGuideInfo() {
		return guideInfo;
	}
	public void setGuideInfo(String guideInfo) {
		this.guideInfo = guideInfo;
	}
	public String getFtpPath() {
		return ftpPath;
	}
	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}
	public String getToPath() {
		return toPath;
	}
	public void setToPath(String toPath) {
		this.toPath = toPath;
	}
	public String getApproveState() {
		return approveState;
	}
	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}
	public Timestamp getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}
	public String getApproveInfo() {
		return approveInfo;
	}
	public void setApproveInfo(String approveInfo) {
		this.approveInfo = approveInfo;
	}
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getEmergency() {
		return emergency;
	}
	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getGuideId() {
		return guideId;
	}
	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}
	public String getXmlStep() {
		return xmlStep;
	}
	public void setXmlStep(String xmlStep) {
		this.xmlStep = xmlStep;
	}
	public String getXmlImage() {
		return xmlImage;
	}
	public void setXmlImage(String xmlImage) {
		this.xmlImage = xmlImage;
	}
	public String getXmlText() {
		return xmlText;
	}
	public void setXmlText(String xmlText) {
		this.xmlText = xmlText;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
}
