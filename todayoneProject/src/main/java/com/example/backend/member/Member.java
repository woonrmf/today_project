package com.example.backend.member;

import java.util.List;

import com.example.backend.BaseTime;
import com.example.backend.feedback.Feedback;
import com.example.backend.likes.Likes;
import com.example.backend.notice.Notice;
import com.example.backend.post.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member extends BaseTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userno;
	
	@Column(unique = true)
	private String username; //닉네임 (화면에 보여줄 닉네임)
	
	@Column(unique = true)
	private String userid;
	
	private String password;
	
	private String birth;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Post> postList;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Notice> noticeList;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Feedback> feedbackList;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	private List<Likes> likesList;
	
}
