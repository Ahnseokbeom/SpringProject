package Practice.service;

import org.springframework.stereotype.Service;

import Practice.Client;
import Practice.dto.MovieDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {
	private final Client client = new Client();
	
	@Transactional(readOnly = true)
	public MovieDto findByKeyword(String keyword) {
		return client.requestMovie(keyword);
	}
}
