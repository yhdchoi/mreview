package com.yhdc.mreview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yhdc.mreview.dto.MovieDTO;
import com.yhdc.mreview.dto.MovieImageDTO;
import com.yhdc.mreview.model.Movie;
import com.yhdc.mreview.model.MovieImage;

public interface MovieService {
    
    Long register(MovieDTO movieDTO);

    default Map<String, Object> dtoTOEntity(MovieDTO movieDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder().mno(movieDTO.getMno()).title(movieDTO.getTitle()).build();

        entityMap.put("Movie", movie);


        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0) {
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {

                MovieImage movieImage = MovieImage.builder().path(movieImageDTO.getPath()).imgName(movieImageDTO.getImgName()).uuid(movieImageDTO.getUuid()).movie(movie).build();
                return movieImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", movieImageList);
        }
        return entityMap;
    }
}
