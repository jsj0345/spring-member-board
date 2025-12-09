<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <%@ include file = "/WEB-INF/views/common/menubar.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="${header.referer }">목록으로</a>
            <br><br>
			
			
	            <table id="contentArea" algin="center" class="table">
	                <tr>
	                    <th width="100">제목</th>
	                    <td colspan="3">${b.boardTitle }</td>
	                </tr>
	                <tr>
	                    <th>작성자</th>
	                    <td>${b.boardWriter }</td>
	                    <th>작성일</th>
	                    <td>${b.createDate }</td>
	                </tr>
	                <tr>
	                    <th>첨부파일</th>
	                    <td colspan="3">
	                    	<c:choose>
	                    		<c:when test="${empty b.originName }">
	                    			첨부파일이 없습니다.
	                    		</c:when>
	                    		<c:otherwise>
			                        <a href="${contextRoot}${b.changeName}" download="${b.originName }">${b.originName }</a>
			                        <%--href는 실제 파일 위치 , download는 다운로드 받을때의 파일 이름 --%>
	                    		</c:otherwise>
	                    	</c:choose>
	                    </td>
	                </tr>
	                <tr>
	                    <th>내용</th>
	                    <td colspan="3"></td>
	                </tr>
	                <tr>
	                    <td colspan="4"><p style="height:150px;">${b.boardContent }</p></td>
	                </tr>
	            </table>
	            <br>
	            
	            
	            <div align="center">
	                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 
	                	 boardUpdate()
	                	 update.bo 
	                	 boardUpdateForm.jsp
	                -->
	                <c:if test="${loginMember.userId eq b.boardWriter}">
	                
<!-- 						<form action="update.bo" method="post"> -->
<%-- 							<input type="hidden" name="bno" value="${b.boardNo }">  --%>
		                	<button type="button" id='updateBtn' class="btn btn-primary">수정하기</button>
<!-- 			            </form> -->
                            <%--매번 form태그를 작성하긴 번거로우니까 메서드를 이용해보자. --%>   


			            
<!-- 						<form action="delete.bo" method="post"> -->
		                	<button type="button" id="deleteBtn" class="btn btn-danger">삭제하기</button>
<!-- 		                </form> -->
	                </c:if>
	            </div>
            
            <script>
            
            	$(function(){
            		replyList(); 
            		//수정하기 또는 삭제하기 버튼 눌렀을때 url로 데이터 접근 및 요청이 불가능하도록 
            		//post방식으로 submit하기 스크립트 구문 작성
            		
            		/*
			           매번 form태그를 적기도 번거로운데 만약에 form태그가 get방식이면?
			           url을 보고 사용자가 마음대로 url링크를 조작해서 아무곳이나 다 들어갈수도있다.
			
			          그래서 post방식으로 작성을한다. 
			        */
            		
            		$("#updateBtn").click(function(){
            			//form태그 및 전달데이터 태그 작성하여 body영역에(문서) 추가하고 실행시키기(submit)
            			
            			//form에는 action과 method 추가 (서버에 요청용 form) 
            			let form = $("<form>").attr("action","update.bo").attr("method","post"); 
            			//type,name,value 추가 (글번호 전달용 input)
            			let input = $("<input>").attr("type","hidden").attr("name","bno").attr("value","${b.boardNo}");
            			
            			/*
            			let input = $("<input>").attr("type","hidden").attr("name","bno").attr("value","${b.boardNo}");
            			
            			여기서 잘 보면 상세보기로 갈때는 모델에 보드 객체를 담아오기 때문에 
            			
            			수정하기 버튼을 누르기 전까지는 모델에 값이 있어서 ${b.boardNo}가 가능하다. 
            			*/
            			
            			
            			
            			//form태그에 input태그 넣기
            			form.append(input);
            			
						//form태그를 body태그영역에 추가하여 submit() 요청하기  			
            			$("body").append(form);
						
						//서버에 submit 요청하기
						form.submit();
            		});
            		
            		
            		/*	
            			삭제하기 버튼 눌렀을때 이벤트 동작으로 처리하기 
            			confirm 을 이용하여 확인을 누르면 동작 처리
            			게시글 삭제 요청 매핑 : delete.bo
            			게시글 삭제 메소드 : deleteBoard() 
            			db데이터를 삭제 한 이후에 해당 게시글에 첨부파일이 있다면 해당 첨부파일도 삭제하기
            		*/
						
            		$("#deleteBtn").click(function(){
            			
            			let flag = confirm("정말 삭제하시겠습니까?");
            			
            			if(flag) {
            				
            				let form = $("<form>").attr("action","delete.bo").attr("method","post");

           	                let input = $("<input>").attr("type","hidden").attr("name","boardNo").attr("value","${b.boardNo}"); // 게시판 글 번호

           	                let input2 = $("<input>").attr("type","hidden").attr("name","originName").attr("value","${b.originName}"); // 게시판 기존 파일 명

           	                let input3 = $("<input>").attr("type","hidden").attr("name","changeName").attr("value","${b.changeName}"); // 바뀐 파일 이름 
           	                
                            form.append(input); 
           	                
           	                form.append(input2);
           	                
           	                form.append(input3); 

           	                $("body").append(form);

           	                form.submit();

            				
            			}
            			
            			
            			/*
            			if(flag){
            				let form = $("<form>").attr("action","delete.bo").attr("method","post");
            				let bnoInput = $("<input>").attr("type","hidden")
            										   .attr("name","bno")
            										   .attr("value","${b.boardNo}");
            				
            				let filePath = $("<input>").attr("type","hidden")
            										   .attr("name","filePath")
            										   .attr("value","${b.changeName}");
            				
            				//form에 input 요소 추가하고 body에 추가하여 submit처리하기
            				form.append(bnoInput,filePath);
            				
            				$("body").append(form);
            				
            				form.submit();
            			}
            			*/ 
            		});
            		
            		
            		
            		
            	});
            	
            	/*
            	    매핑 주소 및 메소드명 : replyList / selectList.re
            	    
            	    해당 상세 페이지 들어오면 비동기 요청 처리되어 댓글 목록 불러오기
            	    
            	    댓글 개수도 추가 (rcount 위치)
            	
            	    model은 VO 제외 board꺼 사용하기 mapper도 마찬가지 
            	*/
            	
            	function replyList() {
				     
				    $.ajax({
				    	url : "selectList.re",
				    	data : {
				    		//어떤 게시글에 달린 댓글인지 알아야하기 때문에 참조 게시글 번호 전달 
				    		refBno : ${b.boardNo}
				    	},
				    	success : function(list){
				    		
				    		console.log(list);
				    		
				    		
							for(let m of list) {
								
								let tr = $("<tr>");
								tr.append($("<td>").text(m.replyWriter)
										  ,$("<td>").text(m.replyContent)
										  ,$("<td>").text(m.createDate));
					
					            $("#replyArea tbody").append(tr);
					            
					             
					            
					            
					
								
							}
							
							$("#replyArea #rcount").text(list.length);
				    		
				    	},
				    	error : function(){
				    		console.log("통신 오류");
				    		
				    	}
				    	
				    })	
				}
            	
            	/*
            	   댓글 등록하기 버튼 누르면 해당 댓글내용 등록처리하기
            	   insertReply.re 매핑명
            	   insertReply() 메소드명
            	   등록 후 목록이 갱신될 수 있도록 처리하기
            	   댓글 등록은 로그인된 상태에서만 가능하도록 처리할것. 
            	   
            	   성공/실패 여부는 alert 메시지로 처리(댓글 등록 성공! / 댓글 등록 실패!)
            	*/
            	
            	
            	/*
            	$(function(){

            	    $("#replyBtn").click(function(){

            	        $.ajax({
            	            url: "insertReply.re",
            	            data: {
            	                replyContent: $("#content").val(),
            	                replyWriter: "${loginMember.userId}",  // <-- 문자열 처리 중요
            	                refBno: ${b.boardNo}
            	            },
            	            success: function(result){
            	                alert(result);
            	            },
            	            error: function(){
            	                alert("댓글 작성 실패!");
            	            }
            	        });

            	    });

            	});
            	*/
            	
            	
            	
            	//console.log("#replyArea>thead>tr button")
            	
            	
            	//내가 짠 코드
            	$(function(){
            		
            		$("#replyArea>thead>tr button").click(function(){
                		
                		$.ajax({
                			url : "insertReply.re",
                			
                			data : {
                				replyContent : $("#replyArea>thead>tr>th #content").val(),
                				//사용자가 내용란에 댓글을 입력하는 것을 갖고오기 
                				//$("#content").val()
                				
                		        replyWriter : "${loginMember.userId}",
                		        //댓글 작성자랑 로그인한 회원의 userId와 같으니까 갖고오기 
                		        
                		        refBno : ${b.boardNo}
                		        //데이터베이스에 댓글을 보관할때 어떤 게시판의 글에서 썼는지를 알고 댓글을 등록해야 하니까
                		        //게시판 참조번호를 현재 상세보기 뷰에 있는 boardNo를 갖고온다. 
                		        
                			},
                			
                			success : function(data) {			
                				alert(data);
                			},
                			
                			error : function() {
                				alert("댓글 등록 실패!");
                			}
                			
                			
                		});
                		
                	}); 
            		
            	});
            	
            	
            	
            	
            	
            </script>
            
            
            
            
            
            <br><br>

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" placeholder="로그인 후 이용가능합니다." id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle">
                           <c:if test="${not empty loginMember}">
                              <button class="btn btn-secondary" id="replyBtn">등록하기</button>
                           </c:if>   
                        </th>
                    </tr>
                    <tr>
                       
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>
                </thead>
                <tbody>
                
                    <!--  
                    <tr>
                        <th>user02</th>
                        <td>ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ꿀잼</td>
                        <td>2020-03-12</td>
                    </tr>
                    -->
                    
                             
                </tbody>
            </table>
        </div>
        <br><br>
        
        <script>
        
		        /*
				    매핑 주소 및 메소드명 : replyList / selectList.re
				    
				    해당 상세 페이지 들어오면 비동기 요청 처리되어 댓글 목록 불러오기
				    
				    댓글 개수도 추가 (rcount 위치)
				
				    model은 VO 제외 board꺼 사용하기 mapper도 마찬가지 
				*/
				
				/*
				function replyList() {
				     
				    $.ajax({
				    	url : "selectList.re",
				    	data : {
				    		//어떤 게시글에 달린 댓글인지 알아야하기 때문에 참조 게시글 번호 전달 
				    		refBno : ${b.boardNo}
				    	},
				    	success : function(list){
				    		
				    		console.log(list);
				    		
				    	},
				    	error : function(){
				    		console.log("통신 오류");
				    		
				    	}
				    	
				    })	
				}
                */
				
				
				/*
				$(function(){
					
					$.ajax({
						url : "selectList.re",
						
						success : function(list) {
							let count = 0; 
							for(let m of list) {
								
								let tr = $("<tr>");
								tr.append($("<td>").text(m.replyWriter)
										  ,$("<td>").text(m.replyContent)
										  ,$("<td>").text(m.createDate));
					
					            $("#replyArea tbody").append(tr);
					            
					            count=count+1; 
					            
					            
					
								
							}
							
							$("#replyArea #rcount").text(count);
						},
						
						error : function(){
							console.log("통신 실패!"); 
						}
					})
					
				})
				*/
				
				/*
				$("#replyBtn").click(function(){
					
					$.ajax({
						url : "insertReply.re",
						data : {
							
							replyContent : $("#content").val(),
							replyWriter : "${loginMember.userId}",
							refBno : ${b.boardNo}
							
						},
						
						success : function(result) {
							
							if(result > 0) {
								alert("댓글 작성 성공!");
							} else {
								alert("댓글 작성 실패!");
							}
							
						},
						
						error : function() {
							       alert("통신 실패!");
						        }
						
					});
					
					
					
				});
				*/
        
        </script>

    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    
</body>
</html>
<%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <%@ include file="/WEB-INF/views/common/menubar.jsp"%>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="${header.referer}">목록으로</a> <!-- 이전으로 -->
            <br><br>
          
            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${board.boardTitle}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${board.boardWriter}</td>
                    <th>작성일</th>
                    <td>${board.createDate}</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    
                    <!-- 
                      <c:choose>
		               <c:when test="${not empty loginMember}">
		               		<a class="btn btn-secondary" style="float:right;" href="${contextRoot}/enroll.bo">글쓰기</a>
		               </c:when>
		               
		               <c:when test="${empty loginMember}">
		                    <a class="btn btn-secondary disabled" style="display: none;" href="">글쓰기</a>
		               </c:when>  
		              </c:choose>
                     -->
                    <c:choose>
                        <c:when test="${not empty board.originName}">
                          <td colspan="3">
                        	<a href="${contextRoot}${board.changeName}" download="${board.changeName}">${board.originName}</a>
                    	  </td>
                    	</c:when>
                    	
                    	<c:when test="${empty board.originName}">
                          <td colspan="3">
                        	<a href="" download=""></a>
                    	  </td>
                    	</c:when>   
                  
                    
                    </c:choose>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${board.boardContent}</p></td>
                </tr>
            </table>
            <br>

            <div align="center">
                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함
                     boardUpdate() 
                     update.bo
                     boardUpdateForm.jsp
                -->
                
                <!-- 아래 조건은 로그인멤버 정보와 게시판 작성자가 서로 같을때랑 다를때를 가지고 수정,삭제버튼 활성화 -->
                
                   	<c:if test="${loginMember.userId eq board.boardWriter}">
					    <!-- <form action="update.bo" method="post"> --> 
						<input type="hidden" name="bno" value="${board.boardNo}">
						<button type="button" class="btn btn-primary" id="updateBtn">수정하기</button>
						
						<button type="button" class="btn btn-danger" id="deleteBtn">삭제하기</button>
					   <!-- </form> -->
				   </c:if>  
                
                  
            </div>
           
            <br><br>

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th>
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>user02</th>
                        <td>ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ꿀잼</td>
                        <td>2020-03-12</td>
                    </tr>
                    <tr>
                        <th>user01</th>
                        <td>재밌어요</td>
                        <td>2020-03-11</td>
                    </tr>
                    <tr>
                        <th>admin</th>
                        <td>댓글입니다!!</td>
                        <td>2020-03-10</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    <script>
       $(function(){
    	   //수정하기 또는 삭제하기 버튼 눌렀을때 url로 데이터 접근 및 요청이 불가능하도록
    	   //port방식으로 submit하기 스크립트 구문 작성
    	   //bno가 웹 브라우저에 url에 보이니까 사용자가 번호를 바꿔서 다른 게시판으로 접근 가능하는걸 막기 위함
    	   
    	   $("#updateBtn").click(function(){
    		   //form 태그 및 전달데이터 태그 작성하여 body영역에(문서) 추가하고 실행시키기(submit)
    		   
    		   //form에는 action과 method 추가 (서버에 요청용 form)
    		   let form = $("<form>").attr("action","update.bo").attr("method","post");
    		   
    		   //type,name,value 추가 (글번호 전달용 input)
    		   let input = $("<input>").attr("type","hidden").attr("name","bno").attr("value","${board.boardNo}");
    		   
    		   console.log(form);
    		   console.log(input);
    		   
    		   //form태그에 input태그 넣기
    		   form.append(input);
    		   
    		   //form태그를 body태그 영역에 추가하여 submit() 요청하기
    		   $("body").append(form);
    		   
    		   //서버에 submit 요청하기
    		   form.submit();
    		   
    		   
    	   });
    	   
    	   /*
    	      삭제하기 버튼 눌렀을때 이벤트 동작으로 처리하기
    	      confirm을 이용하여 확인을 누르면 동작 처리
    	      게시글 삭제 요청 매핑 : delete.bo
    	      게시글 삭제 메소드 : deleteBoard()
    	      db데이터를 삭제 한 이후에 해당 게시글에 첨부파일이 있다면 해당 첨부파일도 삭제하기 
    	   */
    	   
    	   $("#deleteBtn").on("click",function(){
    		   
    		   let form = $("<form>").attr("action","delete.bo").attr("method","post");
    		   
    		   let bnoInput = $("<input>").attr("type","hidden").attr("name","boardNo").attr("value","${board.boardNo}");
    		   
    		   let filePath = $("<input>").attr("type","hidden").attr("name","filePath").attr("value","${board.changeName}");
    		   
    		   form.append(input);
    		   form.append(filePath);
    		   
    		   $("body").append(form);
    		   
    		   if(confirm("정말 삭제하시겠습니까?")) {
    			   
    			   form.submit(); 
    			   
    		   } else {
    			   //취소하면 그냥 버튼누르고 해당 글로 가면된다. 
    			   
    		   }
    		   
    		   
    		   
    	   });
       });
    
    </script>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
</body>
</html>
--%>