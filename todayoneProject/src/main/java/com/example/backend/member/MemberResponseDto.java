package com.example.backend.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
	
	private Integer userno;
	private String username;
	private String userid;
	private String birth;
	private Role role;
}
