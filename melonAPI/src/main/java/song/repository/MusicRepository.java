package song.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import song.entity.Genre;
import song.entity.Music;
import song.entity.MusicType;

public interface MusicRepository extends JpaRepository<Music, Integer>{
	List<Music> findByGenre(Genre genre);

	List<Music> findByType(MusicType musicType);

	List<Music> findByTypeAndGenre(MusicType musicType,Genre genre);
}
