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
    </style>
</head>
<body>
    
    <!-- 메뉴바 -->
    <%@ include file="/WEB-INF/views/common/menubar.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="${contextRoot }/insert.me" method="post">
                <div class="form-group">
                    <label for="inputId">* ID : </label>
                    <input type="text" class="form-control" id="inputId" placeholder="Please Enter ID" name="userId" required> <br>
                    <button type="button" class="form-control" id="duplicate" name="duplicate">중복체크</button>

                    <div id="resultDiv" style="font-size:0.8em; display:none"></div>

                    <label for="inputPwd">* Password : </label>
                    <input type="password" class="form-control" id="inputPwd" placeholder="Please Enter Password" name="userPwd" required> <br>

                    <label for="checkPwd">* Password Check : </label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required> <br>

                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" placeholder="Please Enter Name" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" placeholder="Please Enter Email" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" placeholder="Please Enter Age" name="age" value="0"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-없이)" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" placeholder="Please Enter Address" name="address"> <br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="gender" checked>
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="gender">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary disabled" onclick="return validate();">회원가입</button>
                    <!-- 중복 체크 하기전까진 비활성화 -->
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>
	
	<script>
	    /*
	       사용자가 입력한 아이디가 중복인지 확인하는 작업 비동기식으로 처리하기
	       아이디 입력값이 5글자 이상부터 사용자가 키를 입력 후 떼어질때 이벤트 발생 (또는 버튼을 만들어서 처리해도 됨)
	       중복인지 아닌지 확인하여 네이버에서 봤던 응답데이터처럼 NNNNN(사용불가) 또는 NNNNY(사용가능)
	       으로 응답 데이터를 받아 해당 데이터에 따라서 result div에 사용가능한 아이디입니다. 또는
	       사용 불가능한 아이디 입니다. 라는 텍스트를 출력하기
	       이때 사용 가능한 아이디라면 회원가입 버튼이 활성화 되도록 처리 (기본은 비활성화 처리)
	       메소드명 idCheck()
	       요청 매핑 주소 idcheck.me 
	    */
	    
	    $("#duplicate").click(function(){
	    	let inputId = $("#inputId").val();
	    	
	    	if(inputId.length < 5 ) {
	    		$("#resultDiv").html("아이디는 최소 5글자 이상 입력해야합니다.");
	    		$("#resultDiv").css("display", "block"); // display:none이니까 풀어줘야한다. 
	    		return; 
	    	}
	    	
			
				$.ajax({
		    		url:"idcheck.me",
		    		data : {
		    			
		    			inputId : $("#inputId").val() 
		    		},
		    		
		    		success : function(result) { // 비교를 컨트롤러에서하기  
		    			console.log(result);
		    		
		    		    //일단 통신 성공이면 무조건 결과는 보이게 해야한다. 
		    		
		    			$("#resultDiv").css("display", "block");
		    			
		    		
		    			if(result=='NNNNY') {
		    				$(".btns button[type=submit]").prop("disabled",false);	
		    				$("#resultDiv").html("사용 가능한 아이디입니다.");
		    			} else {
		    				
		    				$("#resultDiv").html("사용 불가능한 아이디입니다.");
		    			}
		    			
		    		},
		    		
		    		error : function(){
		    			console.log("통신 실패"); 
		    		}
		    		
		    		
		    	});
	    		
	    	
	    	
	    	
	    	
	    	
	    })
	    
	    /*
	    $("#inputId").blur(function(){
	    
	    	let length = $("#inputId").val().length;
	    	
	    	if(length>4) {
	    		//5글자 이상일때 서버로 아이디값 전달하며 요청보내기(중복체크)
	    		$.ajax({
	    			url:"idcheck.me",
	    			data : {
	    				inputId : $(""#inputId").val()
	    			},
	    			
	    			success : function(result){
	    				
	    				if(result=="NNNNY") {
	    					$("#resultDiv").html("사용 가능한 아이디입니다.").css("color","green").show();
	    					$(".btns button[type=submit]").prop("disabled",false);
	    					
	    					
	    				} else { //사용 불가 
	    					$("#resultDiv").text("사용 불가능한 아이디입니다.").css('color','red').show();
	    					
	    					//만약에 조건충족한 상태에서 조건에 일치하지 않는 아이디를 입력한다면?(조건($(".btns button[type=submit]").prop("disabled",true);)을 걸지 않았을때) 계속 활성화 상태로 남을테니
	    				    작성해줘야한다.
	    				}
	    				
	    			},
	    			error : function(){
	    				console.log("통신 실패");
	    			}
	    		
	    		})
	    		
	    	} else {
	    		$("resultDiv").show().text("아이디는 5글자 이상 입력하세요.").css('color','green'); 
	    	}
	    	
	    })
	    
	    */
	
		function validate(){
			
			let userPwd = document.querySelector("#inputPwd");
			let checkPwd = document.querySelector("#checkPwd");
			
			
			if(userPwd.value!=checkPwd.value){
				alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
				
				//기본 요청 막아주기
				return false;
			}
		}
	    
	    /*
	    $("#duplicate").click(function(){
	    	$.ajax({
	    		url:"idcheck.me",
	    		
	    		data: {
	    			inputId : $("#inputId").val()	    			
	    		},
	    		
	    		success: function(result) {
	    			
	    			if(result=="NNNNN") {
	    				$("#resultDiv").append("사용 불가능한 아이디입니다.");
	    			} else {
	    				$("#resultDiv").append("사용 가능한 아이디입니다.");
	    				$(".btns button[type=submit]").prop("disabled",false);
	    				
	    				
	    			}
	    		},
	    		
	    		error: function() {
	    			alert("통신 실패!"); 
	    		}
	    		
	    	})
	    });
	    */
	
	</script>



    <!-- 푸터바 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>