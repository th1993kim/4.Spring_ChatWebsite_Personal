package com.webchat.main.stomp.vo;

import org.springframework.web.socket.WebSocketSession;

// 소켓 접속, 접속 해제시의 정보를 보관하는 객체 ,DB 정보가 아니므로 서버 종료시 사라진다.

public class WebSocketSessionInfo {
	
	private WebSocketSession session;
	private Integer conCnt;
	private boolean conStatus;
	
	
	public WebSocketSessionInfo() {
		
	}
	public WebSocketSessionInfo(WebSocketSession session, Integer conCnt, boolean conStatus) {
		this.session = session;
		this.conCnt = conCnt;
		this.conStatus = conStatus;
	}
	
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public Integer getConCnt() {
		return conCnt;
	}
	public void setConCnt(Integer conCnt) {
		this.conCnt = conCnt;
	}
	public boolean isConStatus() {
		return conStatus;
	}
	public void setConStatus(boolean conStatus) {
		this.conStatus = conStatus;
	}
	
	
	
}
