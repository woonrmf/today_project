package com.example.backend;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseTime {
	
	@Column(updatable = false)
	private LocalDateTime credate;
	private LocalDateTime moddate;
	
	@PrePersist
	protected void onCreate() {
		this.credate = LocalDateTime.now();
		this.moddate = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.moddate = LocalDateTime.now();
	}
}
