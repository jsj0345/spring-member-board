package com.kh.spring.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // jsp 페이지로 이동 
@RequestMapping("/websocket") //공통 매핑 레벨 처리
public class WebSocketController {
	
	@GetMapping("/basic") // 위에 websocket 레벨이 있으니 websocket을 생략하고 그 뒤에 레벨 작성 
	public String basic() {
		
		System.out.println("요청 확인");
		
		return "websocket/basic"; 
	}
	
	@GetMapping("/group")
	public String group() {
		System.out.println("요청 확인");
		
		return "websocket/group"; 
	}
	
	@GetMapping("/member")
	public String member() {
		return "websocket/member"; 
	}

}
