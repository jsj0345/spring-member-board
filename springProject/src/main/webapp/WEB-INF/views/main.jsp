<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>

   
   
   <%@include file="/WEB-INF/views/common/menubar.jsp"%>
   
   
   <!-- 
       게시글 중 조회수가 높은 순으로 상위 5개 조회하여 목록 뽑아주기
       매핑주소 topList.bo
       메소드명 topList()
       tbody에 목록화 후 해당 글을 클릭했을때 상세보기 페이지로 이동하기까지 구현 해보기 
    -->
   <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시판</h2> 
   
   			<table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>첨부파일</th>
                    </tr>
                </thead>
                <tbody>
                      
                     
                     
<!--                     <tr> -->
<!--                         <td>5</td> -->
<!--                         <td>마지막 공지사항제목</td> -->
<!--                         <td>admin</td> -->
<!--                         <td>10</td> -->
<!--                         <td>2020-02-10</td> -->
<!--                         <td>★</td> -->
<!--                     </tr> -->
				                
                    
                </tbody>
            </table>
         </div>
      </div>      
     
       <script>
          $(function(){
        	  
        	 $.ajax({
        		 url:"topList.bo",
        		 
        		 success(list){
        			 //처음에 menubar로 접근할땐 자유게시판을 누르는거 아닌 이상 boardListView에 있는 id가 boardList인 태그는 생략 안해도 된다.
        			 //성공을 하고나서 tr td를 붙이면된다. 그럼 result엔 리스트가 들어가야함. 
        			 console.log("통신 성공!");
        			 for(let li of list) { //동적으로 추가 
        				 
        				
        				 let tr = $("<tr>");
        				 tr.append($("<td>").text(li.boardNo)
        				          ,$("<td>").text(li.boardTitle)
        				          ,$("<td>").text(li.boardWriter)
        				          ,$("<td>").text(li.count)
        				          ,$("<td>").text(li.createDate));
        				 
        				 if(li.originName) {
        					 tr.append($("<td>").text('*'));
        				 } else {
        					 tr.append($("<td>").text(''));
        				 }
        				 
        				 // $("<td>").text(b.originName?'*':''); 
        				 
        				 
        				 $("#boardList tbody").append(tr); // tbody에 tr태그 추가.
        				 
        				 //tr 태그에 location.href를 걸때 게시글 번호를 추출한다음 걸기. 
        				 
        				 
        				 
        			 }
        			 
        			 //목록이 다 나왔을테니 글 번호를 추출해서 상세보기 페이지로 이동
        			 //동적으로 추가된 요소에도 이벤트를 걸고자한다면
        			 //상위요소 선택자 -> 하위요소 선택자 이벤트 동작 방식을 이용해야한다.
        			 $("#boardList tbody tr").click(function(){
	   					  let bno = $(this).children().first().text();
	   					  
	   					  location.href="detail.bo?bno="+bno;
   					  
   				 	 });
        			 
        			 /*
        			 동적으로 추가된 요소 (visual studio code에서 4번 JQuery 11번 이벤트 파일보기)
        			 $("#boardList").on("click","tbody tr",function(){
        				 let bno = $(this).children().first().text();
	   					  
	   					 location.href="detail.bo?bno="+bno;
        				 
        			 })
        			 */
        			 
        			 
        			 
        		 },
        		 
        		 error(){
        			 let tr = $("<tr>");
        			 tr.append($("<td>").attr('colspan',"6").text("조회된 데이터가 없습니다."));
        			 
        			 $("#boardList tbody").append(tr); 
        		 }
        	 }) 
          })
       </script>
   

</body>
</html>