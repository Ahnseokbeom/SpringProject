package com.example.animation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "top")
@IdClass(TopId.class)
public class Top {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(unique = true)
	private String name;
	private String episode;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name", referencedColumnName = "title", insertable = false, updatable = false)
    private Quarter quarter;
}
