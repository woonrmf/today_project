package com.example.backend.likes;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class LikesController {
	
	private final LikesService likesService;
	
	@PostMapping("/{postno}/likes")
	public ResponseEntity<LikesDto> toggleLikes(@PathVariable("postno") Integer postno, Authentication authentication) {
		String userid = authentication.getName();
		
		LikesDto dto = likesService.clickLikes(userid, postno);
		return ResponseEntity.ok(dto);
	}
}
