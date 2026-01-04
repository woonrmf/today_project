package com.example.backend.likes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LikesDto {
	
	private boolean liked; //좋아요 상태 (누름, 취소)
	private Integer likeCount; //좋아요 수
}
