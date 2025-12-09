<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #boardList {text-align:center;}
        #boardList>tbody>tr:hover {cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}
        
        #searchForm {
            width:80%;
            margin:auto;
        }
        #searchForm>* {
            float:left;
            margin:5px;
        }
        .select {width:20%;}
        .text {width:53%;}
        .searchBtn {width:20%;}
    </style>
</head>
<body>
    
    <%@ include file= "/WEB-INF/views/common/menubar.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시판</h2>
            <br>
            <!-- 로그인 후 상태일 경우만 보여지는 글쓰기 버튼 -->
            <c:if test="${not empty loginMember }">
	            <a class="btn btn-secondary" style="float:right;" href="${contextRoot}/insert.bo">글쓰기</a>
            </c:if>
            <br>
            <br>
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
				<c:choose>
					<c:when test="${empty list }">
						<tr>
							<td colspan='6'>조회된 게시글이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${list }" var="b"> <!-- 컬렉션 순회  -->
							<tr>
								<td>${b.boardNo }</td>
								<td>${b.boardTitle }</td>
								<td>${b.boardWriter }</td>
								<td>${b.count }</td>
								<td>${b.createDate }</td>
								<td>
									${not empty b.originName?'★':''} <!-- 삼항 연산자 첨부 파일이 존재하면 별, 없으면 '' -->
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>                    
                    
                </tbody>
            </table>
            <br>

			<!-- 
				게시글 눌렀을때 해당 게시글 상세보기 처리하기 
				boardDetail() 메소드 
				detail.bo 매핑주소 
				상세보기 요청 하는 방법은 자유롭게 처리  
			 -->
			 
			 <c:if test="${not empty list }">
			 	<script>
			 	    /*
					$(function(){
						
						$("#boardList tbody tr").click(function(){
							let bno = $(this).children().first().text()
		
							location.href="detail.bo?bno="+bno;					
						});
						
					});
			 	    */
			 	    
			 	   $("#boardList tbody tr").click(function(){
			 		   
                        let bno = $(this).children().first().text();
                        
                        //tr태그의 자식은 td태그다. 
                        //여러 td태그중 첫번째 자식은 번호랑 관련됐다.
                        //번호를 추출. 
                        
                        location.href="detail.bo?bno="+bno; 
                        //눌렀을때 컨트롤러 실행을 해야하니 주소 연결 

			 	   }); 
			 	
			 	
			 	</script>
			 </c:if>
			 
			 <!-- 요청경로 시작 : list.bo 또는 search.bo -->
			 <c:url var="url" value="${empty map?'list.bo':'search.bo'}">
				 
				 <!-- c:url은 내부적으로 URL을 다음과 같이 조립한다.
				      value + ? + name = value
				      그럼 아래도는 list.bo?page= 이렇게까지 붙는것. 
				  -->
				 
				 <c:if test="${not empty map }">
				 	<c:param name="condition">${map.condition}</c:param>
				 	<c:param name="keyword" value="${map.keyword}" />
				 </c:if>
				 
				 
				 <!-- currentPage 값은 페이징바 만드는 반복문에서 각 페이지숫자 넣어줄것 -->
				 <c:param name="page"></c:param>
			 </c:url>
			 
			
            <div id="pagingArea">
                <ul class="pagination">
                    
                    <c:choose>
    					<c:when test="${pi.currentPage eq  1}"> <!-- 현재페이지 1이면 이전버튼 비활성화 -->
		                    <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
    					</c:when>                
    					<c:otherwise>
    						<li class="page-item"><a class="page-link" href="${url}${pi.currentPage-1}">Previous</a></li>
    						<!-- 
    						      Previous를 눌렀을때 현재 페이지 기준 이전 페이지로 돌아간다.
    						      boardController를 보면 @RequestMapping("/list.bo")에서 model 객체에 pi를 담고있기 때문에
    						      값을 참고할수있다. 
    						      아래 구문들도 똑같이 적용된다.  
    						 -->
    					</c:otherwise>
    					
                    </c:choose>
					
					
					<c:forEach begin="${pi.startPage}" end="${pi.endPage}" var="i">
						<!-- el표기법으로 3항연산자를 이용하여 조건이 부합할땐 disabled 속성 넣기 아닐땐 빈값처리 -->
	                    <li class="page-item ${i eq pi.currentPage? 'disabled':'' }"><a class="page-link" href="${url }${i}">${i}</a></li>
                    	
					</c:forEach>					                    
                    
                    <c:choose>
						<c:when test="${pi.currentPage eq pi.maxPage }">
		                    <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link" href="${url}${pi.currentPage+1}">Next</a></li>
						</c:otherwise>                    
                    </c:choose>
                
                
                </ul>
            </div>

            <br clear="both"><br>
			
			<!-- 
				검색 후 선택상자 선택시켜놓기 
			 -->
			 <c:if test="${not empty map }">
				<script>
					$(function(){
						//검색 condition 유지 
						$("option[value=${map.condition}]").attr("selected",true);
					
// 						let condition = "${map.condition}";
// 						$(".custom-select>option").each(function(){
// 							if($(this).val()==condition){
// 								$(this).attr("selected",true);
// 							}
// 							//console.log($(this).val());
// 						});
						
					});
				</script>
			</c:if>

			 
			
			
			
            <form id="searchForm" action="${contextRoot }/search.bo" method="get" align="center">
                <!-- method="get"이니까 쿼리스트링에 값이 다 담겨있다.
                     search.bo?condition=?&keyword=?  
                 -->
                <div class="select">
                    <select class="custom-select" name="condition">
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword" value="${map.keyword}">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            <br><br>
        </div>
        <br><br>

    </div>
    
    

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>
