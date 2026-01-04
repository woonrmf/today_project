package com.example.backend.likes;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.member.Member;
import com.example.backend.member.MemberRepository;
import com.example.backend.post.Post;
import com.example.backend.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {
	
	private final LikesRepository likesRepository;
	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	
	//토글 방식으로 구현 (첫 클릭 : 좋아요 추가, 다시 클릭 : 취소)
	public LikesDto clickLikes(String userid, Integer postno) {
		
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		Post post = postRepository.findById(postno).orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
		
		Optional<Likes> optLike = likesRepository.findByMemberAndPost(member, post);
		
		boolean liked;
		
		if(optLike.isPresent()) {
			likesRepository.delete(optLike.get());
			liked = false;
		} else {
			Likes likes = new Likes();
			likes.setMember(member);
			likes.setPost(post);
			likesRepository.save(likes);
			liked = true;
		}
		
		int likeCount = likesRepository.countByPost(post);
		return new LikesDto(liked, likeCount);
	}
}
