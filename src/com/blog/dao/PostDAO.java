package com.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.blog.dto.PostDTO;

public class PostDAO {
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
	// 글 작성 (글 제목, 카테고리, 글 내용을 받음)
	public int insertPost(String subject, String category, String post_content) throws SQLException {
		String sql = "INSERT INTO post(subject, category, post_content) VALUES (?, ?, ?)";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, category);
			pstmt.setString(3, post_content);
			int result = pstmt.executeUpdate();
			
			log.info("insert result="+result);
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 글 수정 (수정할 글 번호, 제목, 카테고리, 글 내용을 받음)
	public int updatePost(int post_num, String subject, String category, String post_content) throws SQLException {
		String sql = "UPDATE post SET subject=?, category=?, post_content=? WHERE post_num=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, category);
			pstmt.setString(3, post_content);
			pstmt.setInt(4, post_num);
			int result = pstmt.executeUpdate();
			
			log.info("update result="+result);
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 글 삭제 (글 번호를 기준으로 삭제)
	public int deletePost(int post_num) throws SQLException {
		String sql = "DELETE FROM post WHERE post_num=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			int result = pstmt.executeUpdate();
			
			log.info("delete result="+result);
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}	
	// 글 전체 리스트 조회
	public List<PostDTO> getAllList() throws SQLException {
		String sql = "SELECT * FROM post ORDER BY post_num DESC";
		List<PostDTO> list = new ArrayList<>();
		PostDTO bean = null;
		
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);	
			rs=pstmt.executeQuery();
			
			// 글 번호, 글 제목, 카테고리, 글 내용, 작성일, 조회수를 담아 리스트에 저장
			while (rs.next()) {
				bean = new PostDTO();
				bean.setPostNum(rs.getInt("post_num"));
				bean.setSubject(rs.getString("subject"));
				bean.setCategory(rs.getString("category"));
				bean.setPostContent(rs.getString("post_content"));
				bean.setPostDate(rs.getTimestamp("post_date"));
				bean.setReadCount(rs.getInt("read_count"));
				// 리스트에 추가
				list.add(bean);
			}
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		log.info(list.toString());
		return list;
	}
	// 카테고리별 글 리스트 조회
	public List<PostDTO> getCategoryList(String category) throws SQLException {
		String sql = "SELECT * FROM post WHERE category=?";
		List<PostDTO> list = new ArrayList<>();
		
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);	
			pstmt.setString(1, category);
			rs=pstmt.executeQuery();
			
			// 글 번호, 글 제목, 카테고리, 글 내용, 작성일, 조회수를 담아 리스트에 저장
			while (rs.next()) {
				PostDTO bean = new PostDTO();
				bean.setPostNum(rs.getInt("post_num"));
				bean.setSubject(rs.getString("subject"));
				bean.setCategory(rs.getString("category"));
				bean.setPostContent(rs.getString("post_content"));
				bean.setPostDate(rs.getTimestamp("post_date"));
				bean.setReadCount(rs.getInt("read_count"));
				log.info(bean.toString());
				// 리스트에 추가
				list.add(bean);
			}
		} finally {
			
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			
			} catch (SQLException e) {
				log.severe(list.toString());
			}
		}
		log.info(list.toString());
		return list;
	}	
	// 글 상세 보기 (글 번호)
	public PostDTO getPost(int post_num) throws SQLException {
		String sql = "SELECT * FROM post WHERE post_num=?";
		PostDTO bean = new PostDTO();
		
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);	
			pstmt.setInt(1, post_num);
			rs=pstmt.executeQuery();
			
			// 글 번호, 글 제목, 카테고리, 글 내용, 작성일, 조회수를 담아 리스트에 저장
			if (rs.next()) {
				bean.setPostNum(rs.getInt("post_num"));
				bean.setSubject(rs.getString("subject"));
				bean.setCategory(rs.getString("category"));
				bean.setPostContent(rs.getString("post_content"));
				bean.setPostDate(rs.getTimestamp("post_date"));
				bean.setReadCount(rs.getInt("read_count"));
				
				log.info(bean.toString());
			}
			
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		return bean;
	}

}
