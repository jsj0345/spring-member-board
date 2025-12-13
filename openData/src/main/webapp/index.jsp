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
	<h1>공공 데이터 적용해보기</h1>
	
	<h3>미세먼지 경보 현황</h3>
	
	<button id="btn">미세먼지 경보 현황 확인</button>
	<button id="btn2">미세먼지 경보 현황 확인2</button>
	<button id="btn3">미세먼지 경보 현황 확인3</button>
	<button id="btn4">미세먼지 경보 현황 확인4</button>
	
	<script>
	   //버튼 클릭시 api 요청 및 응답데이터 받아보기
	   $("#btn").click(function(){
		   
		   $.ajax({
			   
			   url:"openData.do",
			   success: function(data) {
				   
				   console.log(data); 
				   
				   
				   
			   },
			   error: function(){
				   console.log("통신 오류!");
			   }
			   
		   });
		   
	   });
	   
	   $("#btn2").click(function(){
		   
		   $.ajax({
			   
			   url:"openData2.do",
			   success: function(data) {
				   
				   console.log(data); 
				   
				   
				   
			   },
			   error: function(){
				   console.log("통신 오류!");
			   }
			   
		   });
		   
	   });
	   
	   $("#btn3").click(function(){
		   
		   $.ajax({
			   
			   url:"openData3.do",
			   success: function(data) {
				   
				   //responseStr에서 추출하고자 하는 item목록 추출
				   let items = data.response.body.items; 
				   
				   for(let item of items) {
					   
					   let tr = $("<tr>");
					   
					   tr.append($("<td>").text(item.clearVal),
							     $("<td>").text(item.sn),
							     $("<td>").text(item.dataDate),
							     $("<td>").text(item.districtName),
							     $("<td>").text(item.moveName),
							     $("<td>").text(item.itemCode),
							     $("<td>").text(item.issueGbn),
							     $("<td>").text(item.issueDate),
							     $("<td>").text(item.issueTime),
							     $("<td>").text(item.issueVal),
							     $("<td>").text(item.clearDate),
							     $("<td>").text(item.clearTime));
					   
					   $("tbody").append(tr); 
					   
					   
				   }
				   
				   
				   
				   console.log(data); 
				   
				   
				   
			   },
			   error: function(){
				   console.log("통신 오류!");
			   }
			   
		   });
		   
	   });
	   
	   $("#btn4").click(function(){
		   
		   $.ajax({
			   
			   url:"http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?serviceKey=3e1c30458ee93a89fe3c3f3f12b69bd322eaec6648236559385bdca91af72806&year=2025&returnType=json",
			   success: function(data) {
				   
				   //responseStr에서 추출하고자 하는 item목록 추출
				   let items = data.response.body.items; 
				   
				   for(let item of items) {
					   
					   let tr = $("<tr>");
					   
					   tr.append($("<td>").text(item.clearVal),
							     $("<td>").text(item.sn),
							     $("<td>").text(item.dataDate),
							     $("<td>").text(item.districtName),
							     $("<td>").text(item.moveName),
							     $("<td>").text(item.itemCode),
							     $("<td>").text(item.issueGbn),
							     $("<td>").text(item.issueDate),
							     $("<td>").text(item.issueTime),
							     $("<td>").text(item.issueVal),
							     $("<td>").text(item.clearDate),
							     $("<td>").text(item.clearTime));
					   
					   $("tbody").append(tr); 
					   
					   
				   }
				   
				   
				   
				   console.log(data); 
				   
				   
				   
			   },
			   error: function(){
				   console.log("통신 오류!");
			   }
			   
		   });
		   
	   });
	   
	</script>
	
	<table border="1">
	    <thead>
	        <tr>
	            <th>해제농도</th>
	            <th>일련번호</th>
	            <th>발령일</th>
	            <th>지역명</th>
	            <th>권역명</th>
	            <th>항목명</th>
	            <th>경보단계</th>
	            <th>발령일</th>
	            <th>발령시간</th>
	            <th>발령농도</th>
	            <th>해제일</th>
	            <th>해제시간</th>
	        </tr>
	    </thead>
	    
	    <tbody>
	         
	    
	    </tbody>
	</table>

</body>
</html>