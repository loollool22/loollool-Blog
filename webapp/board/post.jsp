<%@page import="com.blog.dto.CommentDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.blog.dto.PostDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post</title>
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
                        <a href="<%=root %>/board/category1" class="category">카테고리1</a>
                        <a href="<%=root %>/board/category2" class="category">카테고리2</a>
                        <a href="<%=root %>/board/category_none" class="category">카테고리 없음</a>
                        <a href="<%=root %>/guestBook/list" class="category">방명록</a>
                    </div>
                </div>
                <div class="postList">
                	<%
 						PostDTO postBean = (PostDTO)request.getAttribute("postDTO");
 					%>
                    <div class="subjectBox">
                    	<input type="text" id="subject" readonly="readonly" value="<%=postBean.getSubject() %>"/>
                    	<input type="hidden" name="category" value="<%=postBean.getCategory()%>">
                    </div>
                    <div class="contentBox">
                    	<input type="text" id="content" readonly="readonly" value="<%=postBean.getPostContent() %>">
                    	<button type="button" class="editBtn">글 수정</button>
                    	<button type="submit" class="deleteBtn">글 삭제</button>
                    	<input type="hidden" name=postNum value="<%=postBean.getPostNum() %>">
                    </div>
                    <div class="commentBox">
                        <div>
                        	<%	if (id == null) { id = "none"; } %>
                            <input type="text" id="nickname" name="nickname" readonly="readonly" value="<%=id%>"/>
                            <div>
                                <button type="submit">작성</button>
                                <button type="reset">취소</button>
                            </div>
                        </div>
                        <textarea name="commentContent" id="commentContent" placeholder="내용을 입력하세요"></textarea>
                        <input type="text" id="commentDate" readonly="readonly">
                    </div>
                    <%
                    	List<CommentDTO> commentList = null;
                    	commentList = (List<CommentDTO>)request.getAttribute("commentList"); 
                    	for (CommentDTO commentBean : commentList) {
                    %>
                    <div class="commentBox">
                        <div>
                            <input type="text" id="nickname" readonly="readonly" value="<%=commentBean.getNickname() %>">
                            <div>
                                <button type="submit">작성</button>
                                <button type="reset">취소</button>
                            </div>
                        </div>
<%--                         <%	if (commentBean.getCommentContent() == "") { --%>
<!--                         		commentBean.setCommentContent("삭제된 댓글입니다."); -->
<!--                         %> -->
                        <textarea name="commentContent" id="commentContent" readonly="readonly"><%=commentBean.getCommentContent() %></textarea>
<%--                         <%	} %> --%>
                        <input type="text" id="commentDate" readonly="readonly" value="<%=commentBean.getCommentDate() %>">
                    </div>
                    <%	} %>
                </div>
            </div>
            <div class="footer">Copyrights are reserved by loollool.</div>
        </div>
    <script type="text/javascript">
        	$(function () {
				$('.editBtn').click(function () {
					location.href="<%=root %>/board/edit.go?postNum=<%=postBean.getPostNum()%>&category=<%=postBean.getCategory()%>";
				});
				$('.deleteBtn').click(function () {
					location.href="<%=root %>/board/delete.go?postNum=<%=postBean.getPostNum()%>";
				});
			});
    </script>
    </body>
</html>