package com.yhdc.mreview.repositorytest;

import java.util.Arrays;
import java.util.List;

import com.yhdc.mreview.repository.MovieImageRepository;
import com.yhdc.mreview.repository.MovieRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieRepositoryTests {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieImageRepository movieImageRepository;
	
//	@Commit
//	@Transactional
//	@Test
//	public void insertMovies() {
//		
//		IntStream.rangeClosed(1, 50).forEach(i -> {
//			Movie movie = Movie.builder().title("Movie..." + i).build();
//			
//			System.out.println(".........................");
//			
//			movieRepository.save(movie);
//			
//			int count = (int)(Math.random() * 5) + 1;
//			
//			for(int j = 0; j < count; j++) {
//				MovieImage movieImage = MovieImage.builder().uuid(UUID.randomUUID().toString()).movie(movie).imgName("test"+j+".jpg").build();
//				
//				movieImageRepository.save(movieImage);
//				System.out.println("==============================");
//			}
//		});		
//	}
	
//	@Test
//	public void testListPage() {
//		
//		PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "mvno"));
//		
//		Page<Object[]> result = movieRepository.getListPage(pageRequest);
//		
//		for(Object[] objects : result.getContent()) {
//			System.out.println(Arrays.toString(objects));
//		}
//	}
	
	@Test
	public void testGetMovieWithAll() {
		List<Object[]> result = movieRepository.getMovieWithAll(6L);
		
		System.out.println(result);
		
		for (Object[] arr : result) {
			System.out.println(Arrays.toString(arr));
		}
	}
		
	
}
