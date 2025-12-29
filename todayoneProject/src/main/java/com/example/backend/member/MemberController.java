package com.example.backend.member;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<List<MemberResponseDto>> getMemberList() {
		List<MemberResponseDto> memberList = memberService.getMemberList();
		return ResponseEntity.ok(memberList);
	}
	
	@GetMapping("/{userno}")
	public ResponseEntity<MemberResponseDto> getMemberDetail(@PathVariable("userno") Integer userno, Principal principal) {
		MemberResponseDto memberDetail = memberService.getMemberDetail(userno, principal);
		return ResponseEntity.ok(memberDetail);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<MemberResponseDto> signUp(@RequestBody @Valid MemberRequestDto.SignupRequest signupRequest) {
		if(!signupRequest.getPassword().equals(signupRequest.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		MemberResponseDto memberCreate = memberService.memberCreate(signupRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(memberCreate);
	}
	
	@PutMapping("/{userno}")
	public ResponseEntity<MemberResponseDto> modify(@PathVariable("userno") Integer userno, @RequestBody @Valid MemberRequestDto.ModifyRequest modifyRequest, Principal principal) {
	    MemberResponseDto memberModify = memberService.memberModify(userno, modifyRequest, principal);
	    return ResponseEntity.ok(memberModify);
	}
	
	@DeleteMapping("/{userno}")
	public ResponseEntity<Void> delete(@PathVariable("userno") Integer userno, Principal principal) {
		memberService.memberDelete(userno, principal);
		return ResponseEntity.noContent().build();
	}
}
