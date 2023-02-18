<%@page import="com.blog.dto.UserDTO"%>
<%@page import="com.blog.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
	</head>
	<body>
	<%
		// request 저장된 id,pass 파라미터 가져오기
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		// UserDAO 객체생성
		UserDAO userDAO = new UserDAO();
		boolean result = userDAO.login(id, pass);
		
		// if userDTO null이 아니면 아이디 비밀번호 일치 => 세션값 생성 "id",id main.jsp 이동 
		// if userDTO null이면  "아이디 비밀번호 틀림" 뒤로이동
		if(result){
			//아이디 비밀번호 일치
			session.setAttribute("id", id);
			response.sendRedirect("../main.jsp");
		}else{
			// 아이디 비밀번호 틀림
			%>
			<script type="text/javascript">
				alert("아이디 비밀번호 틀림");
				history.back();
			</script>
	<%
		}
	%>
	</body>
</html>