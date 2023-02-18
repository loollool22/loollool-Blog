package com.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.blog.dto.UserDTO;

public class UserDAO {
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
	// 회원 가입
	public int insertOne(String id, String nickname, String pass) throws SQLException {
		String sql = "INSERT INTO user(id, nickname, password) VALUES (?, ?, ?)";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, nickname);
			pstmt.setString(3, pass);
			int result = pstmt.executeUpdate();
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 사용자 정보 변경 - 닉네임과 비밀번호만 변경 가능
	public int updateOne(String id, String nickname, String pass) throws SQLException {
		String sql = "UPDATE user SET nickname=?, password=? WHERE id=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			pstmt.setString(2, pass);
			pstmt.setString(3, id);
			int result = pstmt.executeUpdate();
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 사용자 삭제(id를 기준으로 삭제)
	public int deleteOne(String id) throws SQLException {
		String sql = "DELETE FROM user WHERE id=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int result = pstmt.executeUpdate();
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 사용자 정보 조회 (아이디, 닉네임, 가입일만 조회 가능)
	public UserDTO selectOne(String id) throws SQLException {
		String sql = "SELECT * FROM user WHERE id=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				UserDTO bean = new UserDTO();
				bean.setId(rs.getString("id"));
				bean.setNickname(rs.getString("nickname"));
				bean.setDate(rs.getTimestamp("date"));
				
				return bean;
			}
			
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		return null;
	}
	// 사용자 리스트 조회
	public List<UserDTO> getList() throws SQLException {
		String sql = "SELECT * FROM user";
		List<UserDTO> list = new ArrayList<>();
		
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);	
			rs=pstmt.executeQuery();
			
			// 사용자의 회원넘버, 아이디, 닉네임, 가입일자를 담아 리스트에 저장
			while (rs.next()) {
				UserDTO bean = new UserDTO();
				bean.setNum(rs.getInt("num"));
				bean.setId(rs.getString("id"));
				bean.setNickname(rs.getString("nickname"));
				bean.setDate(rs.getTimestamp("date"));
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
	// 로그인 
	public boolean login(String id, String pass) throws SQLException  {
		String sql = "SELECT id FROM user WHERE id=? and password=?";
		boolean flag = false;
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);	
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			rs=pstmt.executeQuery();
			
			// 사용자의 회원넘버, 아이디, 닉네임, 가입일자를 담아 리스트에 저장
			if (rs.next()) {
				flag = true;
			}
			
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		return flag;
		
	}
}
