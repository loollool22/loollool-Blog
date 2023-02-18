package com.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.blog.dto.CategoryDTO;

public class CategoryDAO {
	Logger log=Logger.getGlobal();
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// DB 연결
	public void getConnection() {
		String url = "jdbc:mysql://localhost:3306/blog";
		String user = System.getenv("MYSQL_USER");
		String password = System.getenv("MYSQL_PW");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			if(conn == null || conn.isClosed())
				conn = DriverManager.getConnection(url, user, password);
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 카테고리 생성
	public int insertCategory(String category) throws SQLException {
		String sql = "INSERT INTO category(category) VALUES (?)";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, category);
			int result = pstmt.executeUpdate();
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 카테고리 이름 변경 (대상 카테고리명, 변경할 카테고리명을 받음)
	public int updateCategory(String category, String newCategory) throws SQLException {
		String sql = "UPDATE category SET category=? WHERE category=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, newCategory);
			pstmt.setString(2, category);
			int result = pstmt.executeUpdate();
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 카테고리 삭제 (카테고리명을 기준으로 삭제)
	public int deleteCategory(String category) throws SQLException {
		String sql = "DELETE FROM category WHERE category=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, category);
			int result = pstmt.executeUpdate();
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}	
	// 카테고리 리스트 조회
	public List<CategoryDTO> getList() throws SQLException {
		String sql = "SELECT * FROM category";
		List<CategoryDTO> list = new ArrayList<>();
		
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);	
			rs=pstmt.executeQuery();
			
			// 카테고리 번호, 카데고리 명을 담아 리스트에 저장
			while (rs.next()) {
				CategoryDTO bean = new CategoryDTO();
				bean.setCategoryNum(rs.getInt("category_num"));
				bean.setCategory(rs.getString("category"));
				// 리스트에 추가
				list.add(bean);
			}
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		return list;
	}
}
