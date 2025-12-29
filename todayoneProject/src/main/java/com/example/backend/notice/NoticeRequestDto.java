package com.example.backend.notice;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class NoticeRequestDto {
	
	@Getter
	@Setter
	public static class NoticeCreateDto {
		@NotBlank(message = "제목을 입력해주세요.")
		private String title;
		@NotBlank(message = "내용을 입력해주세요.")
		private String content;
	}
	
	@Getter
	@Setter
	public static class NoticeModifyDto {
		@NotBlank(message = "제목을 입력해주세요.")
		private String title;
		@NotBlank(message = "내용을 입력해주세요.")
		private String content;
	}
}
