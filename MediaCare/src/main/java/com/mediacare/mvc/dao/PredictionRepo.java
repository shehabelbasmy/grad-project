package com.mediacare.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediacare.entity.Prediction;

@Repository
public interface PredictionRepo extends JpaRepository<Prediction, Integer> {

}
