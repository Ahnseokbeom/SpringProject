package com.example.animation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.animation.entity.Quarter;

public interface QuarterRepository extends JpaRepository<Quarter, String>{
	List<Quarter> findByQuart(String quart);
}
