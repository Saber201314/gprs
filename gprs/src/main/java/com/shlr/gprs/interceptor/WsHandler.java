package com.shlr.gprs.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author xucong
 * @version 创建时间：2017年5月4日 下午10:28:37
 * 
 */
public class WsHandler implements WebSocketHandler {
	Map<String, WebSocketSession> sessionMap=new HashMap<String, WebSocketSession>();
	Map<String, String> nameMap=new HashMap<String, String>();
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("connect to the websocket success......");
		sessionMap.put(session.getId(), session);
		session.sendMessage(new TextMessage("Server:connected OK!"));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		TextMessage returnMessage = new TextMessage(message.getPayload() + " received at server");
		Object payload = message.getPayload();
		String name=payload.toString();
		String[] split = name.split("=");
		if (split.length == 2) {
			nameMap.put(session.getId(), split[1]);
		}
		for (String key : nameMap.keySet()) {
			WebSocketSession webSocketSession = sessionMap.get(key);
			webSocketSession.sendMessage(returnMessage);
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("websocket connection closed......");
		exception.printStackTrace();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("websocket connection closed......");
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
