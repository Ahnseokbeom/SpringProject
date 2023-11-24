package song.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import song.entity.Genre;
import song.entity.Music;
import song.entity.MusicType;
import song.entity.Top;
import song.repository.GenreRepository;
import song.repository.MusicTypeRepository;
import song.service.MusicService;
import song.service.TopService;

@RestController
@RequestMapping("/api")
public class Controller {

	@GetMapping("/csrf")
    public String getCsrfToken(HttpServletRequest request) {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return csrf.getToken();
    }

	private final TopService topService;
	private final MusicService musicService;

	@Autowired
	private MusicTypeRepository musicTypeRepository;
	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	public Controller(TopService topService, MusicService musicService) {
		this.topService = topService;
		this.musicService = musicService;
	}

	// 실시간 한국 top100위까지의 노래
	@GetMapping("/music/top")
	public List<Top> getTop(){
		return topService.getAll();
	}

	// 실시간 한국 top100위 노래 중 원하는 순위의 노래
	@GetMapping("/music/top/{id}")
	public List<Top> getTopId(@PathVariable int id){
		if(topCheck(id)) {
			return (List<Top>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 순위 값입니다.");
		}
		return topService.getId(id);
	}

	// 장르별 노래 순위
	@GetMapping("/music/genre/{genre_id}")
	public List<Music> genreMusic(@PathVariable int genre_id){
		if(genreCheck(genre_id)) {
			return ((List<Music>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 genre 값입니다."));
		}
		return musicService.getGenreMusic(genre_id);
	}

	// 국가별 노래 순위
	@GetMapping("/music/type/{type_id}")
	public List<Music> typeMusic(@PathVariable int type_id){
		if(typeCheck(type_id)) {
			return (List<Music>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 type 값입니다.");
		}
		return musicService.getTypeMusic(type_id);
	}

	// 장르 / 국가별 노래 순위
	@GetMapping("/music/{type_id}/{genre_id}")
	public List<Music> typeByGenreMusic(@PathVariable int type_id, @PathVariable int genre_id){
		List<Integer> list = genreRepository.findDistinctGenreIdByTypeId(type_id);
		if(genreCheck(genre_id)) {
			return (List<Music>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 genre 값입니다.");
		}
		if(typeCheck(type_id)) {
			return (List<Music>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 type 값입니다.");
		}
		if(genreCheck(genre_id) || typeCheck(type_id) || !list.contains(genre_id)) {
				return (List<Music>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 genre 또는 type 값입니다.");
		}
		return musicService.getTypeByGenreMusic(genre_id, type_id);
	}

	// 국가에 대한 정보
	@GetMapping("/type")
	public List<MusicType> getType(){
		return musicTypeRepository.findAll();
	}

	// 장르에 대한 정보
	@GetMapping("/genre")
	public List<Genre> getGenre(){
		return genreRepository.findAll();
	}

	// genre 유효성 검사
	public boolean genreCheck(int genre_id) {
		return genre_id < 1 || genre_id > genreRepository.findMaxGenreValue();
	}

	// type 유효성 검사
	public boolean typeCheck(int type_id) {
		return type_id < 1 || type_id > musicTypeRepository.findMaxTypeValue();
	}

	// 실시간 순위 유효성 검사
	public boolean topCheck(int id) {
		return id < 1 || id > 100;
	}
}
