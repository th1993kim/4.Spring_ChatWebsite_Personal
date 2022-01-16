package com.webchat.main.chat.vo;

import lombok.Data;

@Data
public class WebchatMeberVO {
	private String memberId;
	private String memberPwd;
	private String memberNm;
	private String memberEmail;
	private Integer authority;
	private String delYn;
	
}

