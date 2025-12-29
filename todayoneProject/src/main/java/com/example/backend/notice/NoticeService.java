package com.example.backend.notice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.member.Member;
import com.example.backend.member.MemberRepository;
import com.example.backend.member.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	
	public List<NoticeResponseDto> getNoticeList() {
		return noticeRepository.findAll().stream().map(NoticeResponseDto::from).toList();
	}
	
	public NoticeResponseDto getNoticeDetail(Integer noticeno) {
		Notice notice = noticeRepository.findById(noticeno).orElseThrow(() -> new IllegalArgumentException("해당 항목을 찾을 수 없습니다."));
		return NoticeResponseDto.from(notice);
	}
	
	public NoticeResponseDto noticeCreate(NoticeRequestDto.NoticeCreateDto createDto, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		if(!member.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("관리자 권한이 필요합니다.");
		}
		
		Notice notice = new Notice();
		notice.setTitle(createDto.getTitle());
		notice.setContent(createDto.getContent());
		
		noticeRepository.save(notice);
		return NoticeResponseDto.from(notice);
	}
	
	public NoticeResponseDto noticeModify(NoticeRequestDto.NoticeModifyDto modifyDto, Integer noticeno, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		if(!member.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("관리자 권한이 필요합니다.");
		}
		
		Notice notice = noticeRepository.findById(noticeno).orElseThrow(() -> new IllegalArgumentException("해당 공지를 찾을 수 없습니다."));
		
		notice.setTitle(modifyDto.getTitle());
		notice.setContent(modifyDto.getContent());
		
		return NoticeResponseDto.from(notice);
	}
	
	public void noticeDelete(Integer noticeno, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		if(!member.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("관리자 권한이 필요합니다.");
		}
		
		Notice notice = noticeRepository.findById(noticeno).orElseThrow(() -> new IllegalArgumentException("해당 공지를 찾을 수 없습니다."));
		
		noticeRepository.delete(notice);
	}
}
