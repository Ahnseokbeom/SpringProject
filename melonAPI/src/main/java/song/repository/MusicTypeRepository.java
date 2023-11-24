package song.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import song.entity.MusicType;

public interface MusicTypeRepository extends JpaRepository<MusicType, Integer>{
	  @Query(value = "SELECT MAX(id) FROM music_type", nativeQuery = true)
	   Integer findMaxTypeValue();
}
