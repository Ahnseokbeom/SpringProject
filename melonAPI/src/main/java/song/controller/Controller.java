package song.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import song.dto.MusicDTO;
import song.entity.Genre;
import song.entity.MusicType;
import song.entity.Top;
import song.exception.InvalidRequestException;
import song.repository.GenreRepository;
import song.repository.MusicTypeRepository;
import song.service.MusicService;
import song.service.TopService;

@RestController
@RequestMapping("/api")
public class Controller{

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

    @GetMapping("/music/top")
    public List<Top> getTop() {
        return topService.getAll();
    }

    @GetMapping("/music/genre/{genre_id}")
    public ResponseEntity<List<MusicDTO>> genreMusic(@PathVariable int genre_id) {
        if (genreCheck(genre_id)) {
            throw new InvalidRequestException("유효하지 않은 genre 값입니다.");
        }
        List<MusicDTO> musicList = musicService.getGenreMusic(genre_id);
        return ResponseEntity.ok(musicList);
    }

    @GetMapping("/music/type/{type_id}")
    public ResponseEntity<List<MusicDTO>> typeMusic(@PathVariable int type_id) {
        if (typeCheck(type_id)) {
            throw new InvalidRequestException("유효하지 않은 type 값입니다.");
        }
        List<MusicDTO> musicList = musicService.getTypeMusic(type_id);
        return ResponseEntity.ok(musicList);
    }

    @GetMapping("/music/{type_id}/{genre_id}")
    public ResponseEntity<List<MusicDTO>> typeByGenreMusic(@PathVariable int type_id, @PathVariable int genre_id) {
        List<Integer> list = genreRepository.findDistinctGenreIdByTypeId(type_id);
        if (genreCheck(genre_id) || typeCheck(type_id) || !list.contains(genre_id)) {
            throw new InvalidRequestException("유효하지 않은 genre 또는 type 값입니다.");
        }
        List<MusicDTO> musicList = musicService.getTypeByGenreMusic(genre_id, type_id);
        return ResponseEntity.ok(musicList);
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
