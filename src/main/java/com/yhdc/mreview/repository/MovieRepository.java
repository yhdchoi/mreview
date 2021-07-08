package com.yhdc.mreview.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yhdc.mreview.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("select m, mi, avg(coalesce(r.grade,0)), count(r) from Movie m "
			+ "left outer join MovieImage mi on mi.movie = m " 
			+ "left outer join Review r on r.movie = m group by m ")
	Page<Object[]> getListPage(Pageable pageable);

	// GET THE LAST ADDED IMAGE
//	@Query("select m, i, count(r) from Movie m left join MovieImage i on i.movie = m"
//			+ "and i.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m)"
//			+ "left outer join Review r on r.movie = m group by m")
//	Page<Object[]> getLastImagePage(Pageable pageable);

	// GET ALL FOR A MOVIE
	@Query("select m, mi ,avg(coalesce(r.grade,0)), count(r) "
			+ "from Movie m left outer join MovieImage mi on mi.movie = m "
			+ "left outer join Review r on r.movie = m "
			+ "where m.mvno = :mvno group by mi")
	List<Object[]> getMovieWithAll(@Param("mvno") Long mvno);
}
