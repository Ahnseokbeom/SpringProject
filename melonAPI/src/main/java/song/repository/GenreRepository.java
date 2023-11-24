package song.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import song.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
	  @Query(value = "SELECT MAX(id) FROM genre", nativeQuery = true)
	  Integer findMaxGenreValue();

	  // type_id 입력 시 type_id에 해당하는 genre_id가 아닌 파라미터 값 색출
	  @Query(value = "SELECT DISTINCT(m.genre_id) FROM music_type t JOIN music m ON t.id = m.type_id WHERE m.type_id = ?;", nativeQuery = true)
	  List<Integer> findDistinctGenreIdByTypeId(int typeId);
}
