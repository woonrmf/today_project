package com.example.backend.likes;

import com.example.backend.BaseTime;
import com.example.backend.member.Member;
import com.example.backend.post.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "likes")
public class Likes extends BaseTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer likesno;
	
	@ManyToOne
	private Member member;
	
	@ManyToOne
	private Post post;
	
}
