package com.webchat.main.stomp.controller;

import java.util.Map.Entry;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webchat.main.stomp.configuration.StompWebSocketConfig;
import com.webchat.main.stomp.vo.WebSocketSessionInfo;

@RestController
public class StompMessageController {
	
	@RequestMapping(value = "/stomp/member", method=RequestMethod.GET)
	public String getChatMemberList() throws Exception{
		JSONObject js = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (Entry<String, WebSocketSessionInfo> entry : StompWebSocketConfig.sessionMap.entrySet()) {
			JSONObject json = new JSONObject();
			json.put("ipAddress", entry.getValue().getIpAddress());
			json.put("conCnt", entry.getValue().getConCnt());
			json.put("conStatus", entry.getValue().isConStatus());
			jsonArr.put(json);
		}  
		js.put("member", jsonArr); 
		return js.toString();
	}
	@RequestMapping(value = "/stomp/ss", method=RequestMethod.GET)
	public String getChatMemberList2(){
		return "test";
	}
}
