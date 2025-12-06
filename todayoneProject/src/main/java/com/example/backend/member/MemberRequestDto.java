package com.example.backend.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class MemberRequestDto {
	
	@Getter
	@Setter
	public static class SignupRequest {
		@NotBlank(message = "닉네임 작성은 필수 입니다.")
		private String username;
		@NotBlank(message = "사용하실 ID를 기입해주세요.")
		private String userid;
		@NotBlank(message = "사용하실 비밀번호를 입력해주세요.")
		private String password;
		@NotBlank(message = "비밀번호 확인 해주세요")
		private String passwordConfirm;
		private String birth;
	}
	
	@Getter
	@Setter
	public static class LoginRequest {
		@NotBlank(message = "ID를 입력해주세요.")
		private String userid;
		@NotBlank(message = "비밀번호를 입력해주세요.")
		private String password;
	}
	
	@Getter
	@Setter
	public static class ModifyRequest {
		private String username;
		private String password;
		private String birth;
	}
}
