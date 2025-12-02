package com.example.backend.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	Optional<Member> findByUserid(String userid);
	Optional<Member> findByUsername(String username);
}
