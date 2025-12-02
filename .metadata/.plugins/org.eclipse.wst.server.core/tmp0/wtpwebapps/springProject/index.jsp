<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <h1>환영합니다.</h1>
   
   <!-- 
   시작 경로가 localhost:8080/spring/이다. 
   왜 그럴까? 왼쪽 하단에 Servers에서 톰캣을 누르고 module로 이동하면 
   Path는 /spring으로 적혀있다. 그래서 컨텍스트 루트는 spring이다. 
   
   그리고 서블릿을 기준으로 src/main/webapp으로 파일 경로를 작성하면 된다. 
   
   물론 mapper같은 파일들은 파일을 외부로 배포할때를 기준으로 봐야함. (즉, 컴퓨터가 파일을 해석할수 있는 classpath 경로를 기준으로 봐야함)
   -->

   <jsp:forward page="/WEB-INF/views/main.jsp"></jsp:forward>
</body>
</html>