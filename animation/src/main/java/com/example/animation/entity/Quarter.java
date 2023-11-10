package com.example.animation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "quarter")
public class Quarter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String title;
	private String img;
	private String quart;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "title", referencedColumnName = "name",insertable = false, updatable = false)
//    private Top top;
}
