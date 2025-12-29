package com.example.backend.word;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordResponseDto {
	
	private Integer wordno;
	private String content;
	private Integer userno;
	private LocalDateTime credate;
	private LocalDateTime moddate;
	
	public static WordResponseDto from(Word word) {
		WordResponseDto dto = new WordResponseDto();
		dto.setWordno(word.getWordno());
		dto.setContent(word.getContent());
		dto.setUserno(word.getMember().getUserno());
		dto.setCredate(word.getCredate());
		dto.setModdate(word.getModdate());
		return dto;
	}
}
