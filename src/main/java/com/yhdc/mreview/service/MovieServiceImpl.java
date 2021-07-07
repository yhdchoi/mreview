package com.yhdc.mreview.service;

import java.util.List;
import java.util.Map;

import com.yhdc.mreview.dto.MovieDTO;
import com.yhdc.mreview.model.Movie;
import com.yhdc.mreview.model.MovieImage;
import com.yhdc.mreview.repository.MovieImageRepository;
import com.yhdc.mreview.repository.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    
    private final MovieRepository movieRepository;

    private final MovieImageRepository movieImageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoTOEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");        
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });

        return movie.getMno();
    }

}
