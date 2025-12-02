package com.example.backend.feedback;

import com.example.backend.BaseTime;
import com.example.backend.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "feedback")
public class Feedback extends BaseTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackno;
	
	@Column(length = 45, nullable = false)
	private String title;
	
	@Column(length = 300, nullable = false)
	private String content;
	
	@ManyToOne
	private Member member;
}
