package com.example.backend.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Query("SELECT p FROM Post p LEFT JOIN FETCH p.member") //글이랑 멤버랑 한번에 불러옴 * left join fetch를 안하면 글, 유저 수에 따라 쿼리가 계속 늘어남
	List<Post> findAllWithMember();
	
	@Query("SELECT p FROM Post p " +
			"LEFT JOIN FETCH p.member " +
			"WHERE p.postno = :postno")
	Optional<Post> findByIdWithMember(@Param("postno") Integer postno);
}
