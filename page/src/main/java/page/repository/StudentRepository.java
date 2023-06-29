package page.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import page.entity.Student;
import page.model.Pagination;

public interface StudentRepository extends JpaRepository<Student, Integer>{

	public default List<Student> findAll(Pagination pagination) {
		Page<Student> page = this
				.findAll(PageRequest.of(pagination.getPg() - 1, pagination.getSz(), Sort.Direction.ASC, "id"));
		pagination.setRecordCount((int) page.getTotalElements());
		return page.getContent();
	}
}
