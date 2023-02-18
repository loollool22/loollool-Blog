package com.blog.dto;

import java.sql.Timestamp;

public class CommentDTO {
	// 댓글 번호, 글 번호 , 댓글 그룹
    private int comment_num, post_num, comment_parent_num;
    // 닉네임, 내용
    private String nickname, comment_content;
    // 작성일 
    private Timestamp comment_date;
	
    // getter
    public int getCommentNum() {
		return comment_num;
	}
	public int getPostNum() {
		return post_num;
	}
	public int getCommentParentNum() {
		return comment_parent_num;
	}
	public String getNickname() {
		return nickname;
	}
	public String getCommentContent() {
		return comment_content;
	}
	public Timestamp getCommentDate() {
		return comment_date;
	}
	
	// setter
	public void setCommentNum(int comment_num) {
		this.comment_num = comment_num;
	}
	public void setPostNum(int post_num) {
		this.post_num = post_num;
	}
	public void setCommentParentNum(int comment_parent_num) {
		this.comment_parent_num = comment_parent_num;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setCommentContent(String comment_content) {
		this.comment_content = comment_content;
	}
	public void setCommentDate(Timestamp comment_date) {
		this.comment_date = comment_date;
	}
	
	@Override
	public String toString() {
		return "{\"comment_num\":" + comment_num 
				+ ", \"post_num\":" + post_num 
				+ ", \"comment_parent_num\":" + comment_parent_num 
				+ ", \"nickname\":\"" + nickname 
				+ "\", \"comment_content\":\"" + comment_content
				+ "\", \"comment_date\":\"" + comment_date + "\"}";
	}
	
	
    
    
}
