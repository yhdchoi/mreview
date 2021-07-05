package com.yhdc.mreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yhdc.mreview.model.Member;
import com.yhdc.mreview.model.Movie;
import com.yhdc.mreview.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
	List<Review> findByMovie(Movie movie);
	
	@Modifying
	@Query("delete from Review mr where mr.member = :member")
	void deleteByMember(@Param("member") Member member);
	
	
}
