/**
 * june_web_new:com.june.dto.back.system.base.SysMenuDto.java
 * Created by junehappylove on 2016/12/22
 */
package com.june.dto.back.system.base;

import com.june.common.PageDTO;

/**
 * SysMenuDto:系统菜单 <br>
 *
 * @author 王俊伟 junehappylove(wjw.happy.love@163.com)
 * @date 2016-12-22 17:53
 */
public class SysMenuDto extends PageDTO<SysMenuDto>{

    private static final long serialVersionUID = 90L;

    private String menu_id;
    private String parent_menu_id;
    private String menu_name;
    private String menu_url;
    private String menu_icon;
    private String menu_state;
    private String order_num;
    private String menu_notice;

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getParent_menu_id() {
        return parent_menu_id;
    }

    public void setParent_menu_id(String parent_menu_id) {
        this.parent_menu_id = parent_menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public String getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.menu_icon = menu_icon;
    }

    public String getMenu_state() {
		return menu_state;
	}

	public void setMenu_state(String menu_state) {
		this.menu_state = menu_state;
	}

	public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getMenu_notice() {
        return menu_notice;
    }

    public void setMenu_notice(String menu_notice) {
        this.menu_notice = menu_notice;
    }

    @Override
	public String getDtoName() {
        return "系统菜单";
    }
}

