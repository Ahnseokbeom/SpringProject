package com.example.animation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.animation.Dto.QuarterDTO;
import com.example.animation.Dto.TopDTO;
import com.example.animation.entity.Quarter;
import com.example.animation.entity.Top;
import com.example.animation.repository.QuarterRepository;
import com.example.animation.repository.TopRepository;

@RestController
@RequestMapping("/api")
public class ApiController {
	private final QuarterRepository quarterRepository;
	private final TopRepository topRepository;

	@Autowired
	public ApiController(QuarterRepository quarterRepository, TopRepository topRepository) {
		this.quarterRepository = quarterRepository;
		this.topRepository = topRepository;
	}

	@GetMapping("all")
    public List<QuarterDTO> getAllQuart() {
        List<Quarter> quarters = quarterRepository.findAll();

        // 스트림을 사용하여 title이 ""인 데이터를 필터링하고 DTO로 변환
        List<QuarterDTO> quarterDTOs = quarters.stream()
                .filter(quarter -> !"".equals(quarter.getTitle()))
                .map(quarter -> {
                    QuarterDTO quarterDTO = new QuarterDTO();
                    quarterDTO.setTitle(quarter.getTitle());
                    quarterDTO.setImg(quarter.getImg());
                    quarterDTO.setQuart(quarter.getQuart());
                    return quarterDTO;
                })
                .collect(Collectors.toList());

        return quarterDTOs;
    }

	// localhost:8000/api/quart?quart=??
	@GetMapping("/quart")
	public List<Quarter> getQuart(@RequestParam String quart){
		List<Quarter> findByQuart = quarterRepository.findByQuart(quart);
		return findByQuart;
	}

	@GetMapping("/top20")
	public List<TopDTO> getAllTop() {
	    List<Top> tops = topRepository.findAll();
	    List<TopDTO> topDTOs = new ArrayList<>();

	    for (Top top : tops) {
	        TopDTO topDTO = new TopDTO();
	        topDTO.setId(top.getId());
	        topDTO.setName(top.getName());

	        // Quarter 엔터티에서 img 및 quart 값을 가져와서 DTO에 설정
	        Quarter quarter = top.getQuarter();
	        if (quarter != null) {
	            topDTO.setImg(quarter.getImg());
	            topDTO.setQuart(quarter.getQuart());
	        }

	        topDTOs.add(topDTO);
	    }

	    return topDTOs;
	}

	// localhost:8088/top20/quart?quart=??
	@GetMapping("/top20/quart")
	public List<TopDTO> getTopByQuart(@RequestParam(name = "quart") String quart) {
	    List<Top> tops = topRepository.findAll();
	    List<TopDTO> topDTOs = new ArrayList<>();

	    // Top 엔터티를 quart별로 필터링하여 TopDTO를 생성
	    for (Top top : tops) {
	        // Quarter 엔터티에서 quart 값을 가져와서 DTO에 설정
	        Quarter quarter = top.getQuarter();
	        if (quarter != null) {
	            String topQuart = quarter.getQuart();

	            // URL 매개변수로 받은 quart 값과 일치하는 경우에만 TopDTO에 추가
	            if (quart.equals(topQuart)) {
	                TopDTO topDTO = new TopDTO();
	                topDTO.setName(top.getName());
	                topDTO.setImg(quarter.getImg());
	                topDTO.setQuart(topQuart);
	                topDTOs.add(topDTO);
	            }
	        }
	    }

	    return topDTOs;
	}
}
