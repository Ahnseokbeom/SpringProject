package com.example.animation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.animation.entity.Top;
import com.example.animation.entity.TopId;

public interface TopRepository extends JpaRepository<Top, TopId>{
	List<Top> findByIdBetween(int startRank, int endRank);
}
