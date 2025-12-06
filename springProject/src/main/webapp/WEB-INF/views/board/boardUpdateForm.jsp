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

        #updateForm>table {width:100%;}
        #updateForm>table * {margin:5px;}
    </style>
</head>
<body>
        
	<%@ include file="/WEB-INF/views/common/menubar.jsp" %>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 수정하기</h2>
            <br>

            <form id="updateForm" method="post" action="boardUpdate.bo" enctype="multipart/form-data">
                <!-- 어떤 게시글을 수정할것인지 식별자가 필요하기 때문에 게시글 번호 전달하기 -->
                <!-- boardUpdateForm.jsp로 오기 전에 모델에 board에 관한걸 담았으니 호출 가능하다.  -->
                <input type="hidden" name="boardNo" value="${b.boardNo }">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" value="${b.boardTitle }" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${b.boardWriter }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td>
                        	<c:if test="${not empty b.originName }">
	                        	<!-- 기존에 가지고 있던 첨부파일이 있다면 정보전달하기 -->
	                        	<input type="hidden" name="originName" value="${b.originName }">
	                        	<input type="hidden" name="changeName" value="${b.changeName }">
                        	</c:if>
                            <input type="file" id="upfile" class="form-control-file border" name="uploadFile"> <!-- 새로 업로드 파일 -->
                            현재 업로드된 파일 : <!-- 기존 파일 -->
                            <a href="${contextRoot }${b.changeName}" download="${b.originName }">${b.originName }</a>
                            <!-- savePath는 컨텍스트루트/src/main/webapp/resources/uploadFiles가 다있다.
                                  그런데 웹에서는 컨텍스트루트/바뀐파일명인데 여기서 src/main/webapp이 생략되어있음.  
                             -->
                        </td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required>${b.boardContent }</textarea></td>
                    </tr>
                </table>
                <br>

                <div align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-danger" onclick="javascript:history.go(-1);">이전으로</button>
                </div>
            </form>
        </div>
        <br><br>

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

        #updateForm>table {width:100%;}
        #updateForm>table * {margin:5px;}
    </style>
</head>
<body>
        
    <%@ include file="/WEB-INF/views/common/menubar.jsp"%>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 수정하기</h2>
            <br>

            <form id="updateForm" method="post" action="boardUpdate.bo" enctype="multipart/form-data">
                <input type="hidden" name="boardNo" value="${board.boardNo}">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" value="${board.boardTitle}" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${board.boardWriter}" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td>
                            <c:if test="${not empty board.originName}">
	                            <!-- 기존에 가지고 있던 첨부파일이 있다면 정보전달하기 -->
	                            <input type="hidden" name="originName" value="${board.originName}">
	                            <input type="hidden" name="changeName" value="${board.changeName}">
	                        </c:if>    
                        
                            <input type="file" id="upfile" class="form-control-file border" name="uploadFile">
                            현재 업로드된 파일 : 
                            <a href="${contextRoot}${board.changeName}" download="${board.originName}">${board.originName}</a>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required>${board.boardContent}</textarea></td>
                    </tr>
                </table>
                <br>

                <div align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-danger" onclick="javascript:history.go(-1);">이전으로</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
</body>
</html>
--%>