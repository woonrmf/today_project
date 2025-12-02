package com.example.backend.post;

import java.util.List;

import com.example.backend.BaseTime;
import com.example.backend.likes.Likes;
import com.example.backend.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postno;
	
	@Column(length = 45, nullable = false)
	private String title;
	
	@Column(length = 300, nullable = false)
	private String content;
	
	private Integer status = 1; //공개 1, 비공개 0 기본값 = 공개
	
	@ManyToOne
	private Member member;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Likes> likesList;
}
