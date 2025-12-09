<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
   <h1>AJAX 프로젝트</h1>
   
   <p>
      개요 * <br>
      Asynchronous JavaScript and XML의 약자로 <br>
      서버로부터 데이터를 가져와 전체 페이지를 새로고침하지 않고 일부만 로드할 수 있게 하는 기법 <br>
      기존에 사용하던 a태그 요청 및 form 요청등은 동기식 요청이다. <br>
      동기식 요청은 응답 페이지가 돌아와야 다른 작업을 수행 가능 <br>
      비동기식 요청을 보내기 위해 AJAX 기술을 이용한다. <br>
      
      *동기식 : 요청 처리 후 그에 해당하는 응답페이지가 돌아와야 다음 작업 수행 <br>
              만약 서버에서 호출된 결과까지의 시간이 지연된다면 계속 대기상태 <br>
              전체 페이지가 리로드 된다 (새로고침) <br> <br>
              
      *비동기식 : 현재 페이지를 그대로 유지하며 중간에 추가적인 요청 작업을 할 수 있다. <br>
               다른 페이지로 넘어가는것이 아니라 데이터 통신을 처리하는 것 <br>
               요청을 보내놓고 그에 해당하는 응답이 돌아오기전에도 다른 작업을 수행할 수 있다. <br>
               ex)아이디 중복체크, 검색어 자동완성 등등 <br>
               
      <br>
      비동기식 요청의 단점 <br>
      현재 페이지에 지속적인 리소스가 쌓여 페이지 로드가 느려질 수 있음 <br>
      요청 후 돌아온 응답에 대해 새로운 요소를 만들어 보여주는 작업능력 필요(DOM) <br>
   </p>
   
   
   <pre>
       *jquery 방식으로의 비동기 통신
       $.ajax({
           속성명 : 속성값,
           속성명 : 속성값,
           ...
           
      });
      
      주요 속성
      -url : 요청할 url(변수)
      -type | method : get/post (요청방식 생략시 get)
      -data : 요청할 전달값
      -success : ajax 통신 성공시 실행할 함수
      -error : ajax 통신 실패시 실행할 함수
      -complete : ajax 통신 성공여부 관계없이 실행할 함수
      
      -부수적인 속성
      -async : 서버와의 비동기 처리방식 설정여부 - 동기통신도 가능하도록 하는 설정
      -contentType : request와 데이터 인코딩 타입 정의
      -dataType : 서버에서 response로 오는 데이터 타입 설정, 생략시 자동 추론
      ....
      
   </pre>   
                       
   <h3>1. 요청시 전달값,응답 결과 받아오기</h3>
   이름 : <input type="text" id="name"> <br>
   나이 : <input type="number" id="age"> <br>
   
   <button id="btn1">전송</button>
   
   <div id="result">
   
   </div>
   
   <script>
      $(function(){
    	  
    	  $("#btn1").click(function(){
    		  
    		  $.ajax({
    			  url : 'ajax.do',
    			  data : { // 서버로 보내는 요청 데이터 
    				  name : $("#name").val(),
    				  age : $("#age").val()
    			  },
    			  
    			  success : function(data){ //응답데이터가 있다면 success function의 매개변수로 전달된다.
    				  //function옆에 있는 data는 서버에서 보내는 응답 데이터
    				  console.log(data);
    				  console.log("통신 성공!");
    				  
    				  //응답데이터 원하는 요소에 추가하기
    				  $("#result").html(data); 
    				  
    			  },
    			  
    			  error : function(){
    				  console.log("통신 실패!");
    			  }
    		  });
    		  
    	  });
    	  
    	  
      });
   </script>
   
   <h3>2.요청시 데이터 post요청으로</h3>
   
   메뉴 : <input type="text" id="menu"> <br>
   가격 : <input type="number" id="price"> <br>
   
   <button id="btn2">요청</button>
   
   <div id="result2"></div>
   
   <script>
      //위 메뉴와 가격 데이터를 입력후 버튼을 누르면 결과 div에 OO 메뉴의 가격은 OOO원입니다. 라는 메시지가 출력되도록
      //작성해보기
      //요청 매핑주소는 자유롭게하고 post요청은 위에 작성한 필기 참조하여 속성넣기
      $("#btn2").click(function(){
    	  
    	  $.ajax({
    		  
    		  url : "ajax.dodo",
    		  type: "post",
    		  data : {
    			  menu : $("#menu").val(),
    			  price : $("#price").val()
    		  },
    		  
    		  success : function(data) { //응답 데이터가 있다면 success function의 매개변수로 전달된다. 
    			  console.log(data);
    			  console.log("통신 성공!");
    		  
    		      //응답데이터 원하는 요소에 추가하기
    		      $("#result2").html(data);
    			  
    		  },
    		  
    		  error : function(){
    			  console.log("통신 실패!");
    		  }
    		  
    	  })
    	  
    	  
      })
      
   
   </script>
   
   <h3>3.조회요청 후 조회된 데이터를 여러개 받아 출력해보기</h3>
   조회할 회원 번호 : <input type="number" id="userNo">
   <br>
   <button id="btn3">조회</button>
   
   <div id="result3"></div>
   
   <script>
   
     $(function(){
    	 
    	 $("#btn3").click(function(){
    		 
    		 $.ajax({
    			 url : "select.me",
    			 data : {
    				 userNo : $("#userNo").val()
    				 
    			 },
    			 success : function(result){
    				 
    				 console.log(result);
    				 
    				 let str = "<ul>";
    				 str+="<li>"+result.name+"</li>";
    				 str+="<li>"+result.age+"</li>";
    				 str+="<li>"+result.gender+"</li>";
    				 str+="<li>"+result.email+"</li></ul>";
    				 
    				 $("#result3").html(str);

    				 
    			 },
    			 error : function(){
    				 console.log("통신 실패"); 
    			 }
    		 });
    		 
    	 });
    	 
     });
   
   </script>
   
   <h3>4.조회 요청 후 조회된 데이터 여러개(목록) 처리해보기</h3>
   
   <button id="btn4">JSON으로 목록 조회</button>
   <button id="btn5">GSON으로 목록 조회</button>
   <button id="btn6">응답데이터 자체 목록 조회</button>
   
   <br>
   
   <table border="1" id="memberList">
       <thead>
            <tr>
               <td>이름</td>
               <td>나이</td>
               <td>성별</td>
               <td>이메일</td>
            </tr>
       </thead>
       <tbody>
       </tbody>
   
   </table>
   
   <script>
        $("#btn4").click(function(){
        	 $.ajax({
        		 url : "selectList",
        		 success : function(list){
        			 
        			 console.log(list);
        			 
        			 
        		 },
        		 
        		 error : function(){
        			 console.log("통신 실패");
        		 },
        	 })
        });
        
        $("#btn5").click(function(){
       	 $.ajax({
       		 url : "selectList2",
       		 success : function(list){
       			 
       			 console.log(list);
       			 
       			 
       		 },
       		 
       		 error : function(){
       			 console.log("통신 실패");
       		 },
       	 })
       });
        
        $("#btn6").click(function(){
          	 $.ajax({
          		 url : "selectList3",
          		 success : function(list){
          			 
          			 for(let m of list){
          				 console.log(m);
          				 
          				 let tr = $("<tr>");
          				 tr.append($("<td>").text(m.name),
          						   $("<td>").text(m.age),
          						   $("<td>").text(m.gender),
          						   $("<td>").text(m.email));
          				 
          				 $("#memberList tbody").append(tr); 
          				  
          			 }
          			 
          			 
          		 },
          		 
          		 error : function(){
          			 console.log("통신 실패");
          		 },
          	 })
          }); 
   
   </script>
   

</body>
</html>