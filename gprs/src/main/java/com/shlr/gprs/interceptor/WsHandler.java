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
		sessionMap.put(session.getId(), session);
		session.sendMessage(new TextMessage("连接服务器成功"));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		TextMessage returnMessage = new TextMessage(message.getPayload().toString());
		Object payload = message.getPayload();
		String name=payload.toString();
		String[] split = name.split("=");
		if (split.length == 2) {
			nameMap.put(session.getId(), split[1]);
			for (String key : nameMap.keySet()) {
				WebSocketSession webSocketSession = sessionMap.get(key);
				webSocketSession.sendMessage(new TextMessage(split[1]+"加入聊天室"));
			}
		}else{
			for (String key : nameMap.keySet()) {
				WebSocketSession webSocketSession = sessionMap.get(key);
				webSocketSession.sendMessage(new TextMessage(nameMap.get(session.getId())+":"+payload.toString()));
			}
		}
		
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		if (session.isOpen()) {
			session.close();
			sessionMap.remove(session.getId());
			nameMap.remove(session.getId());
		}
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		sessionMap.remove(session.getId());
		String name = nameMap.get(session.getId());
		nameMap.remove(session.getId());
		for (String key : nameMap.keySet()) {
			WebSocketSession webSocketSession = sessionMap.get(key);
			webSocketSession.sendMessage(new TextMessage(name+"离开了聊天室"));
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
