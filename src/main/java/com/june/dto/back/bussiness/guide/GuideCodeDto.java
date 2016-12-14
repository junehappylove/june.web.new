/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.errorCode.ErrorCodeDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.guide;

import java.io.Serializable;

import com.june.common.PageDTO;

/**
 * 故障代码与指南关联管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class GuideCodeDto extends PageDTO<GuideCodeDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 5574358260968071136L;

	private String guideName;	//	指南名称
	
	private String errorCode; 	// 故障代码
	
	private String guideId; 	// 指南ID
	
	private String errorId;		//故障代码ID
	
	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public GuideCodeDto() {
		super();
	}

	/**
	 * @param guideId
	 * @param errorId
	 */
	public GuideCodeDto(String guideId, String errorId) {
		super();
		this.guideId = guideId;
		this.errorId = errorId;
	}

	/**
	 * @param guideId
	 */
	public GuideCodeDto(String guideId) {
		super();
		this.guideId = guideId;
	}

	@Override
	protected String getDtoName() {
		return "操作指南与故障代码";
	}
}
