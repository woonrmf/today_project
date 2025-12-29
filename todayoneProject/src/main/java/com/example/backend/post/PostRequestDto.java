package com.example.backend.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class PostRequestDto {
		
	@Getter
	@Setter
	public static class PostCreateDto {
		@NotBlank(message = "제목을 입력해주세요.")
		private String title;
		@NotBlank(message = "한 줄을 기록해주세요.")
		private String content;
		private Role status = Role.PUBLIC;
		private Integer userno;
	}
	
	@Getter
	@Setter
	public static class PostModifyDto {
		@NotBlank(message = "제목을 입력해주세요.")
		private String title;
		@NotBlank(message = "한 줄을 기록해주세요.")
		private String content;
		private Role status;
		private Integer userno;
	}
}
