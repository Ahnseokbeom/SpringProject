package Practice.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Controller {
	@GetMapping("api/v1/movies/{keyword}")
	public MovieResponseDto get(@PathVariable String keyword) {
		return 
	}
}
