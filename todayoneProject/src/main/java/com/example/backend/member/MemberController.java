package com.example.backend.member;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
	
	private final MemberService memberService;
		
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody @Valid MemberRequestDto.SignupRequest signupRequest) {
		if(!signupRequest.getPassword().equals(signupRequest.getPasswordConfirm())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "입력하신 비밀번호가 일치하지 않습니다."));
		}
		
		MemberResponseDto memberCreate = memberService.createMember(signupRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(memberCreate);
	}
}
