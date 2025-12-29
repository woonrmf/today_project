package com.example.backend.post;

public enum Role {
	PUBLIC(0, "공개"),
	PRIVATE(1, "비공개");
	
	private final int value;
	private final String label;
	
	Role(int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
}
