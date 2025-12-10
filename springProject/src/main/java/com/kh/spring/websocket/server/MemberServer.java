package com.kh.spring.websocket.server;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kh.spring.member.model.vo.Member;

public class MemberServer extends TextWebSocketHandler{
	
	private Set<WebSocketSession> users = new CopyOnWriteArraySet<>();
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("로그인 서버 접속!");
//		System.out.println(session); //id,uri 
//		System.out.println(session.getAttributes().get("loginMember"));
		
		users.add(session); //접속자 정보 담기
		
		//session.getAttributes().get("세션에 담긴 키값") - 해당 데이터 추출 가능 
		
		
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//메시지 전송 형식 : [아이디] 메시지내용 ( ex) (00:01:01))
		
		Date date = new Date(); // 현재 시간을 뽑아내기 위해 Date 객체 생성 
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss"); //hh:mm:ss형태로 바꾸기 위한것
		
		String time = sdf.format(date); // 시간을 문자열로  
		
		Member loginMember = (Member) session.getAttributes().get("loginMember"); // 로그인 멤버 정보 추출  
		
		String userId = loginMember.getUserId(); // 아이디 추출 
	
		
	    
		
		for(WebSocketSession user : users) {
			
			//message 내용을 조작을 해야하는데 어떻게 해야하지? (BasicServer에서 메시지를 println에 찍었을때 아이디어 얻기)
			
			//message에 있는 getPayload메소드를 이용해보자(상속 받으니까 메서드를 이용 할 수 있다. 이때 getPayload는
			
		    // AbstractWebSocketMessage를 이용
			
			String getMessage = message.getPayload(); // TextMessage 객체에서 원본 추출
			
		    String convertMessage = "[" + userId + "]" + getMessage +"(" + time + ")";
			
			TextMessage textMessage = new TextMessage(convertMessage); // 새로운 객체를 만들어서 textMessage 내용 바꿔주기
			
			// charSequece를 String이 구현!
			
			System.out.println(textMessage);
			
			//근데 객체를 한번 더 생성해야하는데 생성안하고 하는 방법은? 
			
			user.sendMessage(textMessage);
			
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 접속 종료시 동작하는 메소드
		System.out.println("접속종료 : " + session);
				
	    //접속 종료시 접속 종료한 세션정보를 저장소에서 지워주기 (안 지워주면 채팅방에 나가도 메시지가 온다.) 
		users.remove(session); 
				
		System.out.println("남은 접속자 수는 " + users.size()+"명 입니다.");
	}

}
