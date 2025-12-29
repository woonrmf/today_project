package com.example.backend.post;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
	private Integer postno;
	private String title;
	private String content;
	private String username;
	private Integer userno;
	private Role status;
	private Integer likesCount;
	private LocalDateTime credate;
	
	public static PostResponseDto from(Post post) {
		PostResponseDto dto = new PostResponseDto();
		dto.setPostno(post.getPostno());
		dto.setTitle(post.getTitle());
		dto.setContent(post.getContent());
		dto.setUsername(post.getMember().getUsername());
		dto.setUserno(post.getMember().getUserno());
		dto.setStatus(post.getStatus());
		dto.setLikesCount(post.getLikesList() != null ? post.getLikesList().size() : 0);
		dto.setCredate(post.getCredate());
		return dto;
	}
}
