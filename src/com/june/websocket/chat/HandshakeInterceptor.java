/**
 * JUNE软件有限公司<br>
 * com.june.websocket.HandshakeInterceptor.java
 * 日期:2015年12月3日
 */
package com.june.websocket.chat;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 创建握手（handshake）接口 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2015年12月3日 下午3:58:00
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		logger.debug("Before Handshake");
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		logger.debug("After Handshake");
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
