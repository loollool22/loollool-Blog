<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main</title>
        <link rel="stylesheet" type="text/css" href="./css/style.css">
        <%
			String root = request.getContextPath();  
        %>
        <script type="text/javascript" src="<%=root%>/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript">
	        $(function(){
	            $(document).on('click', '#loginBtn', function(e){
	                $('#popup').show();
	                return false;
	            });
	            $(document).on('click', '.popclose', function(e){
	                $('#popup').hide();
	                return false;
	            });
	            $('#popup').hide();
	        });
        </script>
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
                        <img alt="프로필 사진" src="./img/profile.jpg">
                    </div>
                    <div class="categoryBox">
                        <a href="<%=root %>/board/list.go" class="category">전체 리스트</a>
                        <a href="<%=root %>/board/category2" class="category">카테고리1</a>
                        <a href="<%=root %>/board/category3" class="category">카테고리2</a>
                        <a href="<%=root %>/board/category4" class="category">카테고리 없음</a>
                        <a href="<%=root %>/guestBook/list" class="category">방명록</a>
                    </div>
                </div>
                <div class="mainBox">
                    <img alt="메인 사진" src="./img/main.jpg">
                </div>
            </div>
            <div class="footer">Copyrights are reserved by loollool.</div>
        </div>
		<div id="popup">
			<div class="frame">
			    <div class="content">
			        <form method="post" action="<%=root %>/user/login.jsp">
			            <div>
			                <label for="id">ID</label>
			                <input type="text" name="id" id="id"/>
			            </div>
			            <div>
			                <label for="pass">PW</label>
			                <input type="password" name="pass" id="pass"/>
			            </div>
			            <div>
			            	<button type="submit" class="poplogin">로그인</button>
                    		<button type="button" class="popclose">닫기</button>
			            </div>
			        </form>
			    </div>
			</div>
		</div>        
    </body>
    <script type="text/javascript">
	    $(function(){
	        $(document).on('click', '#logoutBtn', function(e){
	        	location.href="<%=root %>/user/logout.jsp?num=<%=id%>";
	        });
	    });
    </script>
</html>