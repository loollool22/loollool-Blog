package com.blog.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.dao.PostDAO;

public class DeletePostController extends HttpServlet {
	Logger log = Logger.getLogger("com.blog.controller.EditController");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		int postNum = Integer.parseInt(req.getParameter("postNum"));

		PostDAO postDAO = new PostDAO();
		try {
			postDAO.deletePost(postNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("list.go");
	}
}
