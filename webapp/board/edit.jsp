<%@page import="com.blog.dto.CategoryDTO"%>
<%@page import="com.blog.dto.CommentDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.blog.dto.PostDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css">
    	<%
			String root = request.getContextPath();  
        %>
    	<script type="text/javascript" src="<%=root%>/js/jquery-1.12.4.min.js"></script>
    </head>
    <body>
    <% request.setCharacterEncoding("utf-8"); %>
        <div id="wrap">
            <div class="header">
                <div class="loginBar">
                    <%
			    		String id = (String)session.getAttribute("id");
			    		if (id == null) { %>
		                    <button id="loginBtn">로그인</button>	
			    	<%	} else {	%>
		                    <button id="logoutBtn">로그아웃</button>
			    	<%	
			    		}
			    	%>
                </div>
                <a href="<%=root %>" id="blogTitle">loollool-log</a>
                <input id="headerSearchBar" placeholder="검색"></input>
            </div>
            <div class="cont">
                <div class="sideBar">
                    <div id="profileBox">
                        <img alt="프로필 사진" src="../img/profile.jpg">
                    </div>
                    <div class="categoryBox">
                        <a href="<%=root %>/board/list.go" class="category">전체 리스트</a>
                        <a href="<%=root %>/board/category2" class="category">카테고리1</a>
                        <a href="<%=root %>/board/category3" class="category">카테고리2</a>
                        <a href="<%=root %>/board/category4" class="category">카테고리 없음</a>
                        <a href="<%=root %>/guestBook/list" class="category">방명록</a>
                    </div>
                </div>
                <div class="postList">
                	<%
 						PostDTO postBean = (PostDTO)request.getAttribute("postDTO");
					%>
					<form method="post" action="<%=root %>/board/edit.go">
						<input type="hidden" name="postNum" value="<%=postBean.getPostNum() %>">					
                    	<div class="subjectBox">
	                    	<input type="text" id="subject" name="subject" value="<%=postBean.getSubject() %>"/></div>
	                    	<select class="category" name="category">
						<%  
							List<CategoryDTO> list = null;
							list = (List<CategoryDTO>)request.getAttribute("categoryList");
							for (CategoryDTO categoryBean : list) { 
						%>
	                    		<option value="<%=categoryBean.getCategory() %>"><%=categoryBean.getCategory() %></option>
	                	<%	} %>
	                    	</select>
	                    <div class="contentBox">
                    		<input type="text" id="content" name="content" value="<%=postBean.getPostContent() %>">
							<button type="submit" class="submitBtn">작성</button>                    		
							<button type="button" class="resetBtn">취소</button>                    		
                    	</div>
                    </form>
                </div>
            </div>
            <div class="footer">Copyrights are reserved by loollool.</div>
        </div>
    </body>
    <script type="text/javascript">
    // 해당 게시글로 돌아가기 (replace로 뒤로 가기 제한)
      	$(function () {
			$('form>div>button:last-child').click(function (e) {
				location.replace("<%=root%>/board/post.go?postNum=<%=postBean.getPostNum()%>");
			});
			
		});
    </script>
</html>