package com.example.backend.feedback;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequestDto {
	
	@NotBlank(message = "제목을 입력하세요.")
	private String title;
	@NotBlank(message = "내용을 입력하세요.")
	private String content;
}
