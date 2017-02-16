package com.june.dto.back.login;

import java.util.List;

import com.june.common.AbstractDTO;

/**
 * 系统菜单
 * MenuDto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年2月16日 下午7:38:15
 * @version 1.0.0
 */
public class MenuDto extends AbstractDTO {

	private static final long serialVersionUID = 20160216;
	// 菜单id
	private String menuId;
	// 菜单的图标地址
	private String icon;
	// 父菜单id
	private String pid;
	// 菜单url
	private String url;
	// 菜单名称
	private String menuName;
	// 菜单顺序
	private Integer orderNum;// 排序数
	// 角色id
	private String roleId;
	// 角色名
	private String roleName;
	
	private Integer infoNum;// 信息数目
	
	private List<MenuDto> menus;	//子菜单
	
	private String clazz;// 设置tab属性是否是选中状态

	private String state;// treegrid展开状态

	public Integer getInfoNum() {
		return infoNum;
	}

	public void setInfoNum(Integer infoNum) {
		this.infoNum = infoNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<MenuDto> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@Override
	protected String getDtoName() {
		return "菜单";
	}

}
