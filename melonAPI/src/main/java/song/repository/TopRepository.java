package song.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import song.entity.Top;

public interface TopRepository extends JpaRepository<Top, Integer>{
	List<Top> findById(int id);
}
