package com.example.backend.feedback;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/feedbacks")
public class FeedbackController {
	
	private final FeedbackService feedbackService;
	
	@GetMapping
	public ResponseEntity<List<FeedbackResponseDto>> getFeedbackList() {
		List<FeedbackResponseDto> getFeedbackList = feedbackService.getFeedbackList();
		return ResponseEntity.ok(getFeedbackList);
	}
	
	@GetMapping("/{feedbackno}")
	public ResponseEntity<FeedbackResponseDto> getFeedbackDetail(@PathVariable("feedbackno") Integer feedbackno) {
		FeedbackResponseDto feedbackDetail = feedbackService.getFeedbackDetail(feedbackno);
		return ResponseEntity.ok(feedbackDetail);
	}
	
	@PostMapping
	public ResponseEntity<FeedbackResponseDto> feedbackCreate(@RequestBody @Valid FeedbackRequestDto createDto, Authentication authentication) {
		String userid = authentication.getName();
		FeedbackResponseDto responseDto = feedbackService.feedbackCreate(createDto, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@PutMapping("/{feedbackno}")
	public ResponseEntity<FeedbackResponseDto> feedbackModify(@PathVariable("feedbackno") Integer feendbackno, @RequestBody @Valid FeedbackRequestDto modifyDto, Authentication authentication) {
		String userid = authentication.getName();
		FeedbackResponseDto responseDto = feedbackService.feedbackModify(modifyDto, feendbackno, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@DeleteMapping("/{feedbackno}")
	public ResponseEntity<Void> feedbackDelete(@PathVariable("feedbackno") Integer feedbacknno, Authentication authentication) {
		String userid = authentication.getName();
		feedbackService.feedbackDelete(feedbacknno, userid);
		return ResponseEntity.noContent().build();
	}
}
