package com.blog.dto;

import java.sql.Timestamp;

public class UserDTO {

    // 회원 번호 
    private int num;
    // 아이디, 닉네임, 비밀번호(SHA512 암호화)
    private String id, nickname, password;
    // 가입일 
    private Timestamp date;
    
    // getter
	public int getNum() {
		return num;
	}
	public String getId() {
		return id;
	}
	public String getNickname() {
		return nickname;
	}
	public String getPassword() {
		return password;
	}
	public Timestamp getDate() {
		return date;
	}
	
	// setter
	public void setNum(int num) {
		this.num = num;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "{\"num\":" + num 
				+ ", \"id\":\"" + id 
				+ "\", \"nickname\":\"" + nickname 
				+ "\", \"password\":\"" + password 
				+ "\", \"date\":\"" + date + "\"}";
	}
    
    
}
