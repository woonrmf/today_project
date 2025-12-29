package com.example.backend.word;

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
public class WordService {
	
	private final WordRepository wordRepository;
	private final MemberRepository memberRepository;
	
	public List<WordResponseDto> getWordList() {
		return wordRepository.findAll().stream().map(WordResponseDto::from).toList();
	}
	
	public WordResponseDto getWordDetail(Integer wordno) {
		Word word = wordRepository.findById(wordno).orElseThrow(() -> new IllegalArgumentException("해당 글감을 찾을 수 없습니다."));
		return WordResponseDto.from(word);
	}
	
	public WordResponseDto wordCreate(WordRequestDto wordRequestDto, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		if(!member.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("관리자 권한이 필요합니다.");
		}
		
		Word word = new Word();
		word.setContent(wordRequestDto.getContent());
		word.setMember(member);
		
		wordRepository.save(word);
		return WordResponseDto.from(word);
	}
	
	public WordResponseDto wordModify(WordRequestDto wordRequestDto, Integer wordno, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		if(!member.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("관리자 권한이 필요합니다.");
		}
		
		Word word = wordRepository.findById(wordno).orElseThrow(() -> new IllegalArgumentException("해당 글감을 찾을 수 없습니다."));
		
		word.setContent(wordRequestDto.getContent());
		return WordResponseDto.from(word);
	}
	
	public void wordDelete(Integer wordno, String userid) {
		Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		if(!member.getRole().equals(Role.ADMIN)) {
			throw new IllegalArgumentException("관리자 권한이 필요합니다.");
		}
		
		Word word = wordRepository.findById(wordno).orElseThrow(() -> new IllegalArgumentException("해당 글감을 찾을 수 없습니다."));
		
		wordRepository.delete(word);
	}
}
