package com.example.backend.member;

import java.util.List;

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
		return new MemberResponseDto(member.getUserno(), member.getUsername(), member.getUserid(), member.getBirth(), member.getRole());
	}
	
	//전체 리스트
	public List<MemberResponseDto> getMemberList() {
		return memberRepository.findAll()
				.stream().map(this::toResponseDto).toList();
	}
	
	//상세 조회
	public MemberResponseDto getMemberDetail(Integer userno) {
		Member member = memberRepository.findById(userno)
				.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		return toResponseDto(member);
	}
	
	//생성(회원가입)
	public MemberResponseDto createMember(MemberRequestDto.SignupRequest memberRequestDto) {
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
	
	//로그인
	public MemberResponseDto MemberLogin(String userid, String password) {
		Member member = memberRepository.findByUserid(userid)
				.orElseThrow(() -> new IllegalArgumentException("가입 된 아이디가 없습니다."));
		
		if(!passwordEncoder.matches(password, member.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		return toResponseDto(member);
	}
	
	//수정
	public MemberResponseDto MemberModify(Integer userno, MemberRequestDto.modifyRequest memberRequestDto) {
		Member member = memberRepository.findById(userno)
				.orElseThrow(() -> new IllegalArgumentException("가입된 회원이 아닙니다."));
		
		member.setUsername(memberRequestDto.getUsername());
		member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
		member.setBirth(memberRequestDto.getBirth());
		
		return toResponseDto(member);
	}
	
	//삭제
	public void MemberDelete(Integer userno) {
		memberRepository.deleteById(userno);
	}
}
