package com.yhdc.mreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yhdc.mreview.model.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

}
