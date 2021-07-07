package com.yhdc.mreview.repositorytest;

import java.util.List;

import com.yhdc.mreview.model.Movie;
import com.yhdc.mreview.model.Review;
import com.yhdc.mreview.repository.ReviewRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewRepositoryTests {

	@Autowired
	private ReviewRepository reviewRepository;

//	@Test
//	public void insertMovieReviews() {
//
//		IntStream.rangeClosed(1, 100).forEach(i -> {
//
//			// Movie Num
//			Long mno = (long)(Math.random() * 50) + 1;
//
//			// Reviewer Num
//			Long mid = ((long)(Math.random() * 50) + 1);
//			
//			Member member = Member.builder().mid(mid).build();
//
//			Review movieReview = Review.builder().member(member).movie(Movie.builder().mvno(mno).build())
//					.grade((int)(Math.random() * 5) + 1).reviewtext("The movie was ...").build();
//
//			reviewRepository.save(movieReview);
//		});
//	}
	
	@Test
	public void testGetMovieReviews() {
		
		Movie movie = Movie.builder().mno(6L).build();
		
		List<Review> result = reviewRepository.findByMovie(movie);
		
		result.forEach(movieReview -> {
			
			System.out.print(movieReview.getReviewnum());
			System.out.print("\t"+movieReview.getGrade());
			System.out.print("\t"+movieReview.getReviewtext());
			System.out.print("\t"+movieReview.getMember().getEmail());
			System.out.println("-----------------------------");
		});
	}
	
	
}
