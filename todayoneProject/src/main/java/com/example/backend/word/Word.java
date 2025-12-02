package com.example.backend.word;

import com.example.backend.BaseTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "word")
public class Word extends BaseTime {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wordno;
	
	private String content;
}
