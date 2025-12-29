package com.example.backend.post;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.member.Member;
import com.example.backend.member.MemberRepository;
import com.example.backend.member.Role;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;

	//리스트
	public List<PostResponseDto> getPostList() {
		return postRepository.findAllWithMember()
				.stream().map(PostResponseDto::from).toList();
	}
	
	//상세조회
	public PostResponseDto getPostDetail(Integer postno) {
		Post post = postRepository.findByIdWithMember(postno)
				.orElseThrow(() -> new IllegalArgumentException("작성한 글을 찾을 수 없습니다."));
		return PostResponseDto.from(post);
	}
	
	//글 작성
	public PostResponseDto postCreate(PostRequestDto.PostCreateDto createDto, String userid) {
		Member member = memberRepository.findByUserid(userid)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		Post post = new Post();
		post.setTitle(createDto.getTitle());
		post.setContent(createDto.getContent());
		post.setStatus(createDto.getStatus());
		post.setMember(member);
		
		postRepository.save(post);
		return PostResponseDto.from(post);
	}
	
	//수정
	public PostResponseDto postModify(PostRequestDto.PostModifyDto modifyDto,Integer postno, String userid) {
		Post post = postRepository.findByIdWithMember(postno)
				.orElseThrow(() -> new IllegalArgumentException("작성한 글을 찾을 수 없습니다."));
		
		if (!post.getMember().getUserid().equals(userid)) {
			throw new IllegalArgumentException("수정 권한이 없습니다.");
		}
		
		post.setTitle(modifyDto.getTitle());
		post.setContent(modifyDto.getContent());
		post.setStatus(modifyDto.getStatus());
		
		return PostResponseDto.from(post);
	}
	
	//삭제
	public void postDelete(Integer postno, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		Post post = postRepository.findByIdWithMember(postno)
				.orElseThrow(() -> new IllegalArgumentException("작성한 글을 찾을 수 없습니다."));
		
		if (!post.getMember().getUserid().equals(userid) && !member.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("삭제 권한이 없습니다.");
		}
		
		postRepository.delete(post);
	}
}
