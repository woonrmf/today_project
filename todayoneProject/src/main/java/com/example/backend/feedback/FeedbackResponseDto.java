package com.example.backend.feedback;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackResponseDto {
	
	private Integer feedbackno;
	private String title;
	private String content;
	private Integer userno;
	private String username;
	private LocalDateTime credate;
	
	public static FeedbackResponseDto from(Feedback feedback) {
		FeedbackResponseDto dto = new FeedbackResponseDto();
		dto.setFeedbackno(feedback.getFeedbackno());
		dto.setTitle(feedback.getTitle());
		dto.setContent(feedback.getContent());
		dto.setUserno(feedback.getMember().getUserno());
		dto.setUsername(feedback.getMember().getUsername());
		dto.setCredate(feedback.getCredate());
		return dto;
	}
}
