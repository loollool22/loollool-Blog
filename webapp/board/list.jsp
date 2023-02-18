<%@page import="com.blog.dto.PostDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All List</title>
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
                    <p id="categoryTitle">전체 리스트</p>
                   	<button class="createPostBtn">글 작성</button>
                    <%
                    	List<PostDTO> list = null;
                    	list = (List<PostDTO>)request.getAttribute("postList"); // object를 List<PostDTO>로 캐스팅
                    	
                    	for (PostDTO bean : list) {                    		
                    %>
                    <a id="postBox" href="<%=root %>/board/post.go?postNum=<%=bean.getPostNum() %>">
                        <div id="postTitle"><%=bean.getSubject() %></div>
                        <div>
                            <div id="categoryValue"><%=bean.getCategory() %></div>
                            <div id="postedTime"><%=bean.getPostDate() %></div>
                        </div>
                    </a>
                    <%	} %>
                </div>
            </div>
            <div class="footer">Copyrights are reserved by loollool.</div>
        </div>
    </body>
    <script type="text/javascript">
    	$(function () {
			$('.createPostBtn').click(function () {
				location.href="<%=root %>/board/add.go";
			});
		});
    </script>
</html>