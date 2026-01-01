package com.example.backend.notice;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/api/notices")
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@GetMapping
	public ResponseEntity<List<NoticeResponseDto>> getNoticeList() {
		List<NoticeResponseDto> noticeList = noticeService.getNoticeList();
		return ResponseEntity.ok(noticeList);
	}
	
	@GetMapping("/{noticeno}")
	public ResponseEntity<NoticeResponseDto> getNoticeDetail(@PathVariable("noticeno") Integer noticeno) {
		NoticeResponseDto noticeDetail = noticeService.getNoticeDetail(noticeno);
		return ResponseEntity.ok(noticeDetail);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<NoticeResponseDto> noticeCreate(@RequestBody @Valid NoticeRequestDto.NoticeCreateDto noticeCreateDto, Authentication authentication) {
		String userid = authentication.getName();
		NoticeResponseDto responseDto = noticeService.noticeCreate(noticeCreateDto, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@PutMapping("/{noticeno}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<NoticeResponseDto> noticeModify(@PathVariable("noticeno") Integer noticeno, @RequestBody @Valid NoticeRequestDto.NoticeModifyDto noticeModifyDto, Authentication authentication) {
		String userid = authentication.getName();
		NoticeResponseDto responseDto = noticeService.noticeModify(noticeModifyDto, noticeno, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@DeleteMapping("/{noticeno}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> noticeDelete(@PathVariable("noticeno") Integer noticeno, Authentication authentication) {
		String userid = authentication.getName();
		noticeService.noticeDelete(noticeno, userid);
		return ResponseEntity.noContent().build();
	}
}
