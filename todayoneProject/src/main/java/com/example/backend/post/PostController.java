package com.example.backend.post;

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
@RequestMapping("/api/posts")
public class PostController {
	private final PostService postService;
	
	@GetMapping
	public ResponseEntity<List<PostResponseDto>> getPostList() {
		List<PostResponseDto> postList = postService.getPostList();
		return ResponseEntity.ok(postList);
	}
	
	@GetMapping("/{postno}")
	public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable("postno") Integer postno) {
		PostResponseDto postDetail = postService.getPostDetail(postno);
		return ResponseEntity.ok(postDetail);
	}
	
	@PostMapping
	public ResponseEntity<PostResponseDto> postCreate(@RequestBody @Valid PostRequestDto.PostCreateDto postCreateDto, Authentication authentication) {
		String userid = authentication.getName();
		
		PostResponseDto responseDto = postService.postCreate(postCreateDto, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@PutMapping("/{postno}")
	public ResponseEntity<PostResponseDto> postModify(@PathVariable("postno") Integer postno, @RequestBody @Valid PostRequestDto.PostModifyDto postModifyDto, Authentication authentication) {
		String userid = authentication.getName();
		
		PostResponseDto responseDto = postService.postModify(postModifyDto, postno, userid);
		return ResponseEntity.ok(responseDto);
	}
	
	@DeleteMapping("/{postno}")
	public ResponseEntity<Void> postDelete(@PathVariable("postno") Integer postno, Authentication authentication) {
		String userid = authentication.getName();
		postService.postDelete(postno, userid);
		return ResponseEntity.noContent().build();
	}
}
