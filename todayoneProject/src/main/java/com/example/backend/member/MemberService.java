package com.example.backend.member;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	//엔티티는 db고 dto는 응답 쪽으로 분리, 보안 문제 등으로 엔티티를 dto로 변환 메서드
	private MemberResponseDto toResponseDto(Member member) {
		return new MemberResponseDto(member.getUserno(), member.getUsername(), member.getUserid(), member.getBirth(), member.getRole(), member.getCredate());
	}
	
	//전체 리스트
	public List<MemberResponseDto> getMemberList() {
		return memberRepository.findAll()
				.stream().map(this::toResponseDto).toList();
	}
	
	//상세 조회
	public MemberResponseDto getMemberDetail(Integer userno, Principal principal) {
		Member member = memberRepository.findById(userno)
				.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		
		if(!member.getUserid().equals(principal.getName())) {
			throw new IllegalStateException("조회 권한이 없습니다.");
		}
		return toResponseDto(member);
	}
	
	//생성(회원가입)
	public MemberResponseDto memberCreate(MemberRequestDto.SignupRequest memberRequestDto) {
		if(memberRepository.findByUserid(memberRequestDto.getUserid()).isPresent()) {
			throw new IllegalStateException("이미 사용 중인 아이디입니다.");
		}
		if(memberRepository.findByUsername(memberRequestDto.getUsername()).isPresent()) {
			throw new IllegalStateException("이미 사용 중인 닉네임입니다.");
		}
		
		Member member = new Member();
		member.setUsername(memberRequestDto.getUsername());
		member.setUserid(memberRequestDto.getUserid());
		member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
		member.setBirth(memberRequestDto.getBirth());
		member.setRole(Role.USER);
		
		memberRepository.save(member);
		return toResponseDto(member);
	}
	
	//수정
	public MemberResponseDto memberModify(Integer userno, MemberRequestDto.ModifyRequest memberRequestDto, Principal principal) {
		Member loginMember = memberRepository.findByUserid(principal.getName())
	            .orElseThrow(() -> new IllegalArgumentException("로그인 정보를 찾을 수 없습니다."));
		
		Member member = memberRepository.findById(userno)
				.orElseThrow(() -> new IllegalArgumentException("가입된 회원이 아닙니다."));
		
		Optional<Member> checkUsername = memberRepository.findByUsername(memberRequestDto.getUsername());
		if(checkUsername.isPresent() && !checkUsername.get().getUserno().equals(userno)) {
			throw new IllegalStateException("이미 사용 중인 닉네임입니다.");
		}
		
		if(!loginMember.getUserno().equals(userno) && !loginMember.getRole().equals(Role.ADMIN)) {
			throw new IllegalStateException("수정 권한이 없습니다.");
		}
		
		member.setUsername(memberRequestDto.getUsername());
		if(memberRequestDto.getPassword() != null && !memberRequestDto.getPassword().isEmpty()) {
		    member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
		} //비밀번호는 선택 비워두면 기존 비밀번호 사용 (
		member.setBirth(memberRequestDto.getBirth());
		
		return toResponseDto(member);
	}
	
	//삭제
	public void memberDelete(Integer userno, Principal principal) {
		Member loginMember = memberRepository.findByUserid(principal.getName())
	            .orElseThrow(() -> new IllegalArgumentException("로그인 정보를 찾을 수 없습니다."));
		
		Member member = memberRepository.findById(userno)
				.orElseThrow(() -> new IllegalArgumentException("가입된 회원이 아닙니다."));
		
		if(!loginMember.getUserno().equals(userno) && !loginMember.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("삭제 권한이 없습니다.");
		}
		
		memberRepository.deleteById(userno);
	}
}
