package Practice;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Practice.dto.MovieDto;

@RequiredArgsConstructor
@Service
public class Client {
    private final RestTemplate restTemplate = new RestTemplate();

    private final String CLIENT_ID = "id";
    private final String CLIENT_SECRET = "password";

    private final String OpenNaverMovieUrl_getMovies = "https://openapi.naver.com/v1/search/movie.json?query={keyword}";

    public MovieDto requestMovie(String keyword) {
        final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다.
        headers.set("X-Naver-Client-Id", CLIENT_ID);
        headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

        final HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(OpenNaverMovieUrl_getMovies, HttpMethod.GET, entity, MovieDto.class, keyword).getBody();
    }
}
