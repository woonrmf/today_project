package com.example.backend.likes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.member.Member;
import com.example.backend.post.Post;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
	
	Optional<Likes> findByMemberAndPost(Member member, Post post); //유저가 좋아요 눌렀는 지 확인
	int countByPost(Post post); //좋아요 수
	void deleteByMemberAndPost(Member member, Post post); //취소
}
