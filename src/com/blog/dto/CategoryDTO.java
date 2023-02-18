package com.blog.dto;

public class CategoryDTO {
	// 카테고리 번호 
    private int category_num;
    // 카테고리 
    private String category;
    
    // getter
	public int getCategoryNum() {
		return category_num;
	}
	public String getCategory() {
		return category;
	}
	
	// setter
	public void setCategoryNum(int category_num) {
		this.category_num = category_num;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "{\"category_num\":" + category_num 
				+ ", \"category\":\"" + category + "\"}";
	}
	
	
    
    
}
