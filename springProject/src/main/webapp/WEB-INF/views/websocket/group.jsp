<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>그룹 채팅 서버</h1>
	<button onclick="connect();">접속</button>
	<button onclick="disconnect();">종료</button>
	
	<hr>
	
	
	<input type="text" id="chat">
	<button onclick="send();">전송</button>
	
	<div id="chatArea"></div>
	
	<script>
		//접속,종료,전송 함수 정의하여 처리해보기 메시지를 받으면 chatArea에 출력하시오 
		
		let socket;
		
		function connect(){
			
			//192.168.30.22
			//let url = "ws://localhost:8080/spring/group";
			let url = "ws://localhost:8080/spring/group";
			
			if(!socket){//소켓없을때만 처리 
				socket = new WebSocket(url);
			}
			
			//연결이 되었을때
			socket.onopen = function(){
				console.log("연결 처리");
			}
			
			//메시지 받았을때 
			socket.onmessage = function(message){

				let div = document.querySelector("#chatArea"); 
				
				//메시지 데이터
				let newDiv = document.createElement("div");
				newDiv.textContent = message.data;
				
				div.append(newDiv); //텍스트 담은 div 넣어주기
				
			}
			
			//연결 종료 되었을때
			socket.onclose = function(){
				let msg = document.createElement("h3");
				msg.textContent = "접속종료";
				
				document.querySelector("#chatArea").append(msg);	
			}
			
			//에러 발생시
			socket.onerror = function(e){
				console.log(e);
			}
		}
		
		function send(){
			
			let input = document.querySelector("#chat");
			
			socket.send(input.value);
			
			input.value="";//값 지우기
			
			
		}
		
		function disconnect(){
			socket.close();
		}
		
		
		
	</script>
	
	
</body>
</html>