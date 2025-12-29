package com.example.backend.notice;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeResponseDto {
	private Integer noticeno;
	private String title;
	private String content;
	private Integer userno;
	private String username;
	private LocalDateTime credate;
	
	public static NoticeResponseDto from(Notice notice) {
		NoticeResponseDto dto = new NoticeResponseDto();
		dto.setNoticeno(notice.getNoticeno());
		dto.setTitle(notice.getTitle());
		dto.setContent(notice.getContent());
		dto.setUserno(notice.getMember().getUserno());
		dto.setUsername(notice.getMember().getUsername());
		dto.setCredate(notice.getCredate());
		return dto;
	}
}
