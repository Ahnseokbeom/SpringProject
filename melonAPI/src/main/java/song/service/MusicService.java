package song.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import song.entity.Genre;
import song.entity.Music;
import song.entity.MusicType;
import song.repository.MusicRepository;

@Service
public class MusicService {
	private final MusicRepository musicRepository;

	@Autowired
	public MusicService(MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
	}

	public List<Music> getGenreMusic(int genre_id){
		Genre genre = new Genre();
		genre.setId(genre_id);
		return musicRepository.findByGenre(genre);
	}
	public List<Music> getTypeMusic(int type_id){
		MusicType type = new MusicType();
		type.setId(type_id);
		return musicRepository.findByType(type);
	}
	public List<Music> getTypeByGenreMusic(int genre_id, int type_id){
		MusicType type = new MusicType();
		type.setId(type_id);
		Genre genre = new Genre();
		genre.setId(genre_id);
		return musicRepository.findByTypeAndGenre(type, genre);
	}
}
