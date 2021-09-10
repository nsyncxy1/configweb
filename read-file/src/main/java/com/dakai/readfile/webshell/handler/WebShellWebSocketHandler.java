

package com.dakai.readfile.webshell.handler;

import com.dakai.readfile.webshell.service.WebShellService;
import com.dakai.readfile.webshell.utils.WebShellUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.annotation.Resource;


/**
 * WebSocket处理器
 *
 * @author zmzhou
 * @version 1.0
 * @title WebShellWebSocketHandler
 * @date 2021/2/22 20:58
 */
@Slf4j
@Component
public class WebShellWebSocketHandler implements WebSocketHandler {
	@Resource
	private WebShellService webShellService;

	/**
	 * 用户连接上WebSocket回调
	 * @param webSocketSession WebSocketSession
	 * @author zmzhou
	 * @date 2021/2/23 20:35
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) {
		log.info("用户:{},连接Web Shell", WebShellUtils.getUuid(webSocketSession));
		//调用初始化连接
		webShellService.initConnection(webSocketSession);
	}

	/**
	 * 收到消息回调
	 * @param webSocketSession WebSocketSession
	 * @param webSocketMessage WebSocketMessage
	 * @author zmzhou
	 * @date 2021/2/23 20:41
	 */
	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
		if (webSocketMessage instanceof TextMessage) {
			log.info("用户:{},发送命令:{}", WebShellUtils.getUuid(webSocketSession), webSocketMessage.getPayload());
			//调用service接收消息
			webShellService.recvHandle(((TextMessage) webSocketMessage).getPayload(), webSocketSession);
		} else if (webSocketMessage instanceof BinaryMessage) {
			log.info("BinaryMessage:{}", webSocketMessage);
		} else if (webSocketMessage instanceof PongMessage) {
			log.info("PongMessage:{}", webSocketMessage);
		} else {
			log.error("Unexpected WebSocket message type: " + webSocketMessage);
		}
	}

	/**
	 * 错误的回调
	 * @param webSocketSession WebSocketSession
	 * @author zmzhou
	 * @date 2021/2/23 20:41
	 */
	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
		log.error("用户:{},数据传输错误:{}", WebShellUtils.getUuid(webSocketSession), throwable);
	}

	/**
	 * 连接关闭的回调
	 * @param webSocketSession WebSocketSession
	 * @author zmzhou
	 * @date 2021/2/23 20:43
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
		log.info("用户:{},断开webSocket连接:{}", WebShellUtils.getUuid(webSocketSession), closeStatus);
		//调用service关闭连接
		webShellService.close(webSocketSession);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
