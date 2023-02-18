package com.blog.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.blog.dao.UserDAO;
import com.blog.dto.UserDTO;

public class UserController extends HttpServlet {
	Logger log = Logger.getLogger("com.blog.controller.UserController");
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		
		UserDAO userDAO = new UserDAO();
		HttpSession session = req.getSession();
		try {
			if (userDAO.login(id, pass)) {
				session.setAttribute("id", id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("main.jsp");
	}
	
	
}
