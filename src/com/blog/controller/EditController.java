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

import com.blog.dao.CategoryDAO;
import com.blog.dao.PostDAO;
import com.blog.dto.CategoryDTO;
import com.blog.dto.PostDTO;

public class EditController extends HttpServlet {
	Logger log = Logger.getLogger("com.blog.controller.EditController");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매개변수로 쓸 게시글 번호를 가져옴
		int postNum = Integer.parseInt(req.getParameter("postNum"));
		PostDAO postDAO = new PostDAO();
		CategoryDAO categoryDAO = new CategoryDAO();
		
		try {
			PostDTO postBean = postDAO.getPost(postNum);
			List<CategoryDTO> categoryList = categoryDAO.getList();
			req.setAttribute("postDTO", postBean);
			req.setAttribute("categoryList", categoryList);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		RequestDispatcher rd = req.getRequestDispatcher("edit.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		int postNum = Integer.parseInt(req.getParameter("postNum"));
		String subject = req.getParameter("subject");
		String category = req.getParameter("category");
		String content= req.getParameter("content");

		PostDAO postDAO = new PostDAO();
		try {
			postDAO.updatePost(postNum, subject, category, content);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("post.go?postNum="+postNum);
	}
}
