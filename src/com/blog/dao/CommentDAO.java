package com.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.blog.dto.CommentDTO;

public class CommentDAO {
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
	// 댓글 작성 (글 번호, 닉네임, 댓글 내용을 받음)
	public int insertComment(int post_num, String nickname, String comment_content) throws SQLException {
		// 기본적으로 댓글은 초기에 comment_parent_num 값이 NULL이다.
		String sql = "INSERT INTO comment(post_num, nickname, comment_content, comment_parent_num) VALUES (?, ?, ?, NULL)";
		// 하지만 대댓글과 같은 parent를 가지게 하기 위해서는 이 값을 자기 참조된 값으로 바꾸어 주어야 한다.
			// 이게 최선? 셀프 조인으로도 써보자
		String sql2 = "UPDATE comment"
						+ " SET comment_parent_num ="
							+ " (SELECT max_comment_num"
								+ " FROM (SELECT max(comment_num) AS max_comment_num"
									+ " FROM comment) AS subquery)";
		sql2 +=	" WHERE comment_num ="
				+ " (SELECT max_comment_num"
					+ " FROM (SELECT max(comment_num) AS max_comment_num"
						+ " FROM comment) AS subquery)";
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setString(2, nickname);
			pstmt.setString(3, comment_content);
			int result = pstmt.executeUpdate();
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			
			if (result != 0) {				
				pstmt.executeUpdate(sql2);
			}
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 대댓글 작성 (글 번호, 닉네임, 댓글 내용, 원 댓글 번호를 받음)
		// 원 댓글의 번호(comment_num)를 comment_parent_num에 저장한다. 
	public int insertReplyComment(int post_num, String nickname, String comment_content, int comment_num) throws SQLException {
		String sql = "INSERT INTO comment(post_num, nickname, comment_content, comment_parent_num) VALUES (?, ?, ?, ?)";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setString(2, nickname);
			pstmt.setString(3, comment_content);
			pstmt.setInt(4, comment_num);
			int result = pstmt.executeUpdate();
			
			log.info("insert result="+result);
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 댓글 수정 (글 번호, 수정할 댓글 번호, 댓글 내용을 받음)
	public int updateComment(int post_num, int comment_num, String comment_content) throws SQLException {
		String sql = "UPDATE comment SET comment_content=? WHERE post_num=? && comment_num=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, comment_content);
			pstmt.setInt(2, post_num);
			pstmt.setInt(3, comment_num);
			int result = pstmt.executeUpdate();
			
			log.info("update result="+result);
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 사용자의 댓글 삭제 (논리적 삭제 / 글 번호와 댓글 번호를 받음) 
		// 사용자가 삭제 요청 시, 닉네임과 댓글 내용을 없애는 방식 
		//	-> DB에는 'unknown', '' / 화면에는 '알 수 없는 사용자', '삭제된 댓글입니다'로 출력
	public int userDeleteComment(int post_num, int comment_num) throws SQLException {
		String sql = "UPDATE comment SET nickname='unknown', comment_content='' WHERE post_num=? && comment_num=?";
		
		try {
			getConnection();
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, comment_num);
			int result = pstmt.executeUpdate();
			
			log.info("delete result="+result);
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}
	// 관리자의 댓글 삭제 (물리적 삭제 / 글 번호와 댓글 번호를 받음)
		// 댓글 번호를 기준으로 삭제 
	public int[] adminDeleteComment(int post_num, int comment_num) throws SQLException {
		// parent_num이 comment_num을 자기 참조하는 제약 조건에 의해 바로 삭제 불가
			// -> parent_num을 null로 바꾸어 준 뒤 삭제 진행
		String sql = "UPDATE comment SET comment_parent_num=NULL WHERE post_num=? && comment_num=?";
		String sql2 = "DELETE FROM comment WHERE post_num=? && comment_num=?";
		
		try {
			getConnection();
			
			int[] result = new int[2];
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, post_num);
			pstmt.setInt(2, comment_num);
			result[0] = pstmt.executeUpdate();

			if (result[0] != 0) {
				pstmt=conn.prepareStatement(sql2);
				pstmt.setInt(1, post_num);
				pstmt.setInt(2, comment_num);
				result[1] = pstmt.executeUpdate();				
			}
			log.info("delete result="+result);
			// 수행된 행의 수 or 0(=> 반환하지 않는 SQL문) 반환
			return result;
			
		} finally {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	}	
	// 모든 댓글 리스트 조회
	public List<CommentDTO> getAllCommentList() throws SQLException {
		String sql = "SELECT * FROM comment ORDER BY comment_parent_num ASC";
		List<CommentDTO> list = new ArrayList<>();
		
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);	
			rs=pstmt.executeQuery();
			
			// 댓글 번호, 댓글이 달린 글의 번호, 원 댓글 번호, 댓글 작성자의 닉네임, 댓글 내용, 댓글 작성 일자를 담아 리스트에 저장
			while (rs.next()) {
				CommentDTO bean = new CommentDTO();
				bean.setCommentNum(rs.getInt("comment_num"));
				bean.setPostNum(rs.getInt("post_num"));
				bean.setCommentParentNum(rs.getInt("comment_parent_num"));
				bean.setNickname(rs.getString("nickname"));
				bean.setCommentContent(rs.getString("comment_content"));
				bean.setCommentDate(rs.getTimestamp("comment_date"));
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
	// 특정 글의 댓글 리스트 조회
	public List<CommentDTO> getPostCommentList(int post_num) throws SQLException {
		String sql = "SELECT * FROM comment WHERE post_num=? ORDER BY comment_parent_num ASC";
		List<CommentDTO> list = new ArrayList<>();
		
		try {
			getConnection();
			pstmt=conn.prepareStatement(sql);	
			pstmt.setInt(1, post_num);
			rs=pstmt.executeQuery();
			
			// 댓글 번호, 댓글이 달린 글의 번호, 원 댓글 번호, 댓글 작성자의 닉네임, 댓글 내용, 댓글 작성 일자를 담아 리스트에 저장
			while (rs.next()) {
				CommentDTO bean = new CommentDTO();
				bean.setCommentNum(rs.getInt("comment_num"));
				bean.setPostNum(rs.getInt("post_num"));
				bean.setCommentParentNum(rs.getInt("comment_parent_num"));
				bean.setNickname(rs.getString("nickname"));
				bean.setCommentContent(rs.getString("comment_content"));
				bean.setCommentDate(rs.getTimestamp("comment_date"));
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
}
