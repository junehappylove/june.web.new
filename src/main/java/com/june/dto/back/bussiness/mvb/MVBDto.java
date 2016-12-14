/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.errorCode.ErrorCodeDto.java
 * 日期:2016年5月9日
 */
package com.june.dto.back.bussiness.mvb;

import java.io.Serializable;

import com.june.common.PageDTO;

/**
 * MVB管理 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月9日 下午5:38:44
 */
public class MVBDto extends PageDTO<MVBDto> implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7192588942879864877L;
	
	private String mvbId;// 主键
	private String system;// 系统
	private String portAddr;// 端口地址
	private int portSize;// 端口大小
	private int cycleTime;// 循环时间
	private int byteOffset;// 字节偏移
	private int bitOffset; // 位偏移
	private String signal; // 信号量
	private String description;// 描述
	private long max;// 最大值
	private long min;// 最小值
	private String type;// 类型
	private String measure;// 范围
	private String formula;// 规则
	private String unit;// 单位
	private String flag;// 标识
	private long upper;// 上限值
	private int lower;// 下限值
	private int defaultValue;// 默认
	private String vehicle;// 车型ID
	private String vehicleName;// 车型名称
	public String getMvbId() {
		return mvbId;
	}
	public void setMvbId(String mvbId) {
		this.mvbId = mvbId;
	}
	public String getSystem() {
		system = system.trim();
		return system;
	}
	public void setSystem(String system) {
		system = system.trim();
		this.system = system;
	}
	public String getPortAddr() {
		return portAddr;
	}
	public void setPortAddr(String portAddr) {
		this.portAddr = portAddr;
	}
	public int getPortSize() {
		return portSize;
	}
	public void setPortSize(int portSize) {
		this.portSize = portSize;
	}
	public int getCycleTime() {
		return cycleTime;
	}
	public void setCycleTime(int cycleTime) {
		this.cycleTime = cycleTime;
	}
	public int getByteOffset() {
		return byteOffset;
	}
	public void setByteOffset(int byteOffset) {
		this.byteOffset = byteOffset;
	}
	public int getBitOffset() {
		return bitOffset;
	}
	public void setBitOffset(int bitOffset) {
		this.bitOffset = bitOffset;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public long getUpper() {
		return upper;
	}
	public void setUpper(long upper) {
		this.upper = upper;
	}
	public int getLower() {
		return lower;
	}
	public void setLower(int lower) {
		this.lower = lower;
	}
	public int getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	@Override
	protected String getDtoName() {
		return "MVB";
	}

}
