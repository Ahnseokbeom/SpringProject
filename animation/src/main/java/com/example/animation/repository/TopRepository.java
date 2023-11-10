package com.example.animation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.animation.entity.Top;
import com.example.animation.entity.TopId;

public interface TopRepository extends JpaRepository<Top, TopId>{

}
