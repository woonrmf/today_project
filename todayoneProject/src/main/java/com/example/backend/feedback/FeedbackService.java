package com.example.backend.feedback;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.member.Member;
import com.example.backend.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;
	private final MemberRepository memberRepository;
	
	public List<FeedbackResponseDto> getFeedbackList() {
		return feedbackRepository.findAll().stream().map(FeedbackResponseDto::from).toList(); 
	}
	
	public FeedbackResponseDto getFeedbackDetail(Integer feedbackno) {
		Feedback feedback = feedbackRepository.findById(feedbackno).orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
		return FeedbackResponseDto.from(feedback);
	}
	
	public FeedbackResponseDto feedbackCreate(FeedbackRequestDto requestDto, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
		
		Feedback feedback = new Feedback();
		feedback.setTitle(requestDto.getTitle());
		feedback.setContent(requestDto.getContent());
		feedback.setMember(member);
		
		feedbackRepository.save(feedback);
		return FeedbackResponseDto.from(null);
	}
	
	public FeedbackResponseDto feedbackModify(FeedbackRequestDto requestDto, Integer feedbackno, String userid) {
		Feedback feedback = feedbackRepository.findById(feedbackno).orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
		
		if(!feedback.getMember().getUserid().equals(userid)) {
			throw new IllegalArgumentException("수정 권한이 없습니다.");
		}
		
		feedback.setTitle(requestDto.getTitle());
		feedback.setContent(requestDto.getContent());
		
		return FeedbackResponseDto.from(feedback);
	}
	
	public void feedbackDelete(Integer feedbackno, String userid) {
		Feedback feedback = feedbackRepository.findById(feedbackno).orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));
		
		if(!feedback.getMember().getUserid().equals(userid)) {
			throw new IllegalArgumentException("삭제 권한이 없습니다.");
		}
		
		feedbackRepository.delete(feedback);
	}
}
