<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>Basic 채팅 페이지</h1>
  
  <button onclick="connect();">접속</button>
  <button onclick="disconnect();">접속 종료</button>
  
  <hr>
  
  <input type="text" id="chat">
  <button onclick="send();">전송</button>
  
  <div id="chatArea"></div>
  
  <script>
     //웹소켓 접속 및 메시지 전달, 접속 종료 함수 정의
     
     let socket; // 웹 소켓 생성하여 담을 변수 준비
     
     //접속 함수
     function connect(){
    	 
    	 let url = "ws://localhost:8080/spring/basic"; // 접속 요청 경로(웹소켓 접속 경로) https가 아니다.
    	 //servlet-context 참고 
    	 
    	 //접속 경로를 넣고 웹소켓 객체 생성
    	 socket = new WebSocket(url);
    	 
    	 console.dir(socket); // << 해보기 
    	 
    	 //접속 되었을때
    	 socket.onopen = function(){
    		 
    		 console.log("연결 성공!");
    	 }
    	 
    	 //접속 종료되었을때 
    	 socket.onclose = function(){
    		 console.log("연결 종료!");
    	 }
    	 
    	 //에러 발생했을때 동작
    	 socket.onerror = function(e){
    		 console.log("에러 발생");
    		 console.log(e); // 에러 정보 출력
    	 }
    	 
    	 //메시지 수신했을때 동작
    	 socket.onmessage = function(message){
    		 console.log("메시지가 도착했습니다.");
    		 console.log(message); 
    		 
    		 document.querySelector("#chatArea").innerHTML += message.data + "<br>"; 
    	 }
    	 
    	 
    	 
     }
     
     //메시지 전송 함수
     function send(){
    	 //사용자가 텍스트를 입력한 다음 전송버튼을 누르면 해당 내용을 소켓에 전달하기 
    	 let msg = document.querySelector("#chat"); 
    	 
    	 socket.send(msg.value); // 소켓으로 메시지 전송 send는 소켓의 속성  (서버로 메시지 전송)
    	 
    	 //onmessage는 send를 호출하고나서 서버가 클라이언트로 메시지를 보내면 연결되는 속성 
    	 
    	 msg.value = ""; // 입력값 지워주기 
     }
     
     
     //접속 종료 함수
     function disconnect(){
    	 socket.close(); // 웹소켓 연결 닫기 
     }
  </script>
  
  
</body>
</html>