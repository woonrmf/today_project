package com.example.backend.word;

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
@RequestMapping("/api/words")
public class WordController {
	private final WordService wordService;
	
	@GetMapping
	public ResponseEntity<List<WordResponseDto>> getWordList() {
		List<WordResponseDto> wordList = wordService.getWordList();
		return ResponseEntity.ok(wordList);
	}
	
	@GetMapping("/{wordno}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<WordResponseDto> getWordDetail(@PathVariable("wordno") Integer wordno) {
		WordResponseDto wordDetail = wordService.getWordDetail(wordno);
		return ResponseEntity.ok(wordDetail);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<WordResponseDto> wordCreate(@RequestBody @Valid WordRequestDto wordRequestDto, Authentication authentication) {
		String userid = authentication.getName();
		WordResponseDto responseDto = wordService.wordCreate(wordRequestDto, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@PutMapping("/{wordno}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<WordResponseDto> wordModify(@PathVariable("wordno") Integer wordno, @RequestBody @Valid WordRequestDto wordRequestDto, Authentication authentication) {
		String userid = authentication.getName();
		WordResponseDto responseDto = wordService.wordModify(wordRequestDto, wordno, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@DeleteMapping("/{wordno}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> wordDelete(@PathVariable("wordno") Integer wordno, Authentication authentication) {
		String userid = authentication.getName();
		wordService.wordDelete(wordno, userid);
		return ResponseEntity.noContent().build();
	}
}
