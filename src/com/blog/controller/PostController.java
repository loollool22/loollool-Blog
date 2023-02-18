package com.blog.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.dao.CommentDAO;
import com.blog.dao.PostDAO;
import com.blog.dto.CommentDTO;
import com.blog.dto.PostDTO;

public class PostController extends HttpServlet {
	Logger log = Logger.getLogger("com.blog.controller.PostController");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매개변수로 쓸 게시글 번호를 가져옴
		int postNum = Integer.parseInt(req.getParameter("postNum"));
		PostDAO postDAO = new PostDAO();
		CommentDAO commentDAO = new CommentDAO();
		
		try {
			PostDTO postBean = postDAO.getPost(postNum);
			List<CommentDTO> commentList = commentDAO.getPostCommentList(postNum);
			req.setAttribute("postDTO", postBean);
			req.setAttribute("commentList", commentList);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		RequestDispatcher rd = req.getRequestDispatcher("post.jsp");
		rd.forward(req, resp);
	}
}
