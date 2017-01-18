/**
 * JUNE软件有限公司<br>
 * june_web_new:com.june.websocket.chat.MiniChatHandler.java
 * 日期:2016年10月19日
 */
package com.june.websocket.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.june.robot.qas.QasAnswerImpl;
import com.june.utility.StringUtil;

/**
 * 新建一个自己的HandShakeInterceptor类
 * HandShakeInterceptor是websocket握手拦截器，用于拦截websocket初始化连接的请求 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年10月19日 下午10:17:07
 */
@Controller
public class MiniChatHandler extends TextWebSocketHandler {
	private static final Logger log = LoggerFactory.getLogger(MiniChatHandler.class);
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 接收到客户端消息时调用
		super.handleTextMessage(session, message);
		String clientMessage = message.getPayload();
		log.debug("接受客户端ID[" + session.getId() + "]-内容为:" + clientMessage);
		// TODO 这里进行高级信息处理业务
		clientMessage = StringUtil.randomCode(clientMessage.length());
		clientMessage = QasAnswerImpl.instance().getAnswer("王俊伟是谁？").getContent();
		TextMessage returnMessage = new TextMessage("" + clientMessage);
		// 将处理后的返回给客户端
		session.sendMessage(returnMessage);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 与客户端完成连接后调用
		log.debug("afterConnectionEstablished");
		log.debug("getId:" + session.getId());
		log.debug("getLocalAddress:" + session.getLocalAddress().toString());
		log.debug("getTextMessageSizeLimit:" + session.getTextMessageSizeLimit());
		log.debug("getUri:" + session.getUri().toString());
		log.debug("getPrincipal:" + session.getPrincipal());
		session.sendMessage(new TextMessage(("您好,"+session.getPrincipal()).getBytes()));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// 消息传输出错时调用
		super.handleTransportError(session, exception);
		log.debug("handleTransportError");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 一个客户端连接断开时关闭
		super.afterConnectionClosed(session, status);
		log.debug("afterConnectionClosed");
	}

	@Override
	public boolean supportsPartialMessages() {
		log.debug("supportsPartialMessages");
		return super.supportsPartialMessages();
	}
}
