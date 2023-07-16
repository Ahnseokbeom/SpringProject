package backend.entity;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{

	List<Board> findAllByDeleteYn(final char deleteYn, final Sort sort);

}
