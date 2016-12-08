package com.june.dto.back.common;


import java.util.Date;

/**
 * (SYS_ORG)
 * 
 * @author caiyang
 * @version 1.0.0
 */
public class SysOrgDto implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -883775885175559465L;
    
    /** 组织ID */
    private Double orgId;
    
    /** 地区ID */
    private Double districtId;
    
    /** 组织名称 */
    private String orgName;
    
    /** 上级组织ID */
    private Double parentOrgId;
    
    /** 领导 */
    private String orgLeader;
    
    /** 注释 */
    private String remark;
    
    /** 删除标志 */
    private String delFlag;
    
    /** 添加用户 */
    private String addUserId;
    
    /** 添加时间 */
    private Date addTime;
    
    /** 更新用户 */
    private String updateUserId;
    
    /** 更新时间 */
    private Date updateTime;
    
    /** 版本号 */
    private Double version;
    
    /**
     * 获取组织ID
     * 
     * @return 组织ID
     */
     public Double getOrgId() {
        return this.orgId;
     }
     
    /**
     * 设置组织ID
     * 
     * @param orgId
     *          组织ID
     */
     public void setOrgId(Double orgId) {
        this.orgId = orgId;
     }
    
    /**
     * 获取地区ID
     * 
     * @return 地区ID
     */
     public Double getDistrictId() {
        return this.districtId;
     }
     
    /**
     * 设置地区ID
     * 
     * @param districtId
     *          地区ID
     */
     public void setDistrictId(Double districtId) {
        this.districtId = districtId;
     }
    
    /**
     * 获取组织名称
     * 
     * @return 组织名称
     */
     public String getOrgName() {
        return this.orgName;
     }
     
    /**
     * 设置组织名称
     * 
     * @param orgName
     *          组织名称
     */
     public void setOrgName(String orgName) {
        this.orgName = orgName;
     }
    
    /**
     * 获取上级组织ID
     * 
     * @return 上级组织ID
     */
     public Double getParentOrgId() {
        return this.parentOrgId;
     }
     
    /**
     * 设置上级组织ID
     * 
     * @param parentOrgId
     *          上级组织ID
     */
     public void setParentOrgId(Double parentOrgId) {
        this.parentOrgId = parentOrgId;
     }
    
    /**
     * 获取领导
     * 
     * @return 领导
     */
     public String getOrgLeader() {
        return this.orgLeader;
     }
     
    /**
     * 设置领导
     * 
     * @param orgLeader
     *          领导
     */
     public void setOrgLeader(String orgLeader) {
        this.orgLeader = orgLeader;
     }
    
    /**
     * 获取注释
     * 
     * @return 注释
     */
     public String getRemark() {
        return this.remark;
     }
     
    /**
     * 设置注释
     * 
     * @param remark
     *          注释
     */
     public void setRemark(String remark) {
        this.remark = remark;
     }
    
    /**
     * 获取删除标志
     * 
     * @return 删除标志
     */
     public String getDelFlag() {
        return this.delFlag;
     }
     
    /**
     * 设置删除标志
     * 
     * @param delFlag
     *          删除标志
     */
     public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
     }
    
    /**
     * 获取添加用户
     * 
     * @return 添加用户
     */
     public String getAddUserId() {
        return this.addUserId;
     }
     
    /**
     * 设置添加用户
     * 
     * @param addUserId
     *          添加用户
     */
     public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
     }
    
    /**
     * 获取添加时间
     * 
     * @return 添加时间
     */
     public Date getAddTime() {
        return this.addTime;
     }
     
    /**
     * 设置添加时间
     * 
     * @param addTime
     *          添加时间
     */
     public void setAddTime(Date addTime) {
        this.addTime = addTime;
     }
    
    /**
     * 获取更新用户
     * 
     * @return 更新用户
     */
     public String getUpdateUserId() {
        return this.updateUserId;
     }
     
    /**
     * 设置更新用户
     * 
     * @param updateUserId
     *          更新用户
     */
     public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
     }
    
    /**
     * 获取更新时间
     * 
     * @return 更新时间
     */
     public Date getUpdateTime() {
        return this.updateTime;
     }
     
    /**
     * 设置更新时间
     * 
     * @param updateTime
     *          更新时间
     */
     public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
     }
    
    /**
     * 获取版本号
     * 
     * @return 版本号
     */
     public Double getVersion() {
        return this.version;
     }
     
    /**
     * 设置版本号
     * 
     * @param version
     *          版本号
     */
     public void setVersion(Double version) {
        this.version = version;
     }
}