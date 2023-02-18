package com.blog.dto;

import java.sql.Timestamp;

public class PostDTO {
	// 글 번호, 조회수
	private int post_num, read_count;
	// 제목, 카테고리, 내용 
	private String subject, category, post_content;
	// 작성일 
	private Timestamp post_date;
	
	// getter
	public int getPostNum() {
		return post_num;
	}
	public int getReadCount() {
		return read_count;
	}
	public String getSubject() {
		return subject;
	}
	public String getCategory() {
		return category;
	}
	public String getPostContent() {
		return post_content;
	}
	public Timestamp getPostDate() {
		return post_date;
	}
		
	// setter
	public void setPostNum(int post_num) {
		this.post_num = post_num;
	}
	public void setReadCount(int read_count) {
		this.read_count = read_count;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setPostContent(String post_content) {
		this.post_content = post_content;
	}
	public void setPostDate(Timestamp post_date) {
		this.post_date = post_date;
	}
	
	@Override
	public String toString() {
		return "{\"post_num\":" + post_num 
				+ ", \"read_count\":" + read_count 
				+ ", \"subject\":\"" + subject 
				+ "\", \"category\":\"" + category 
				+ "\", \"post_content\":\"" + post_content 
				+ "\", \"post_date\":\"" + post_date + "\"}";
	}
	 
	
}
