package com.june.common;

/**
 * 
 * MessageDto <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2016年12月20日 下午9:22:17
 * @version 1.0.0
 */
public class MessageDto extends AbstractDTO {
	private static final long serialVersionUID = 8792801322474995370L;

	@Override
	protected String getDtoName() {
		return "消息(Message)";
	}
}
