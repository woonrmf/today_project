package com.example.backend.member;

public enum Role {
	ADMIN(0, "관리자"),
	USER(1, "일반인");
	
	private final int value;
	private final String label;
	
	Role(int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return value;
	}
	
	public String label() {
		return label;
	}
}
