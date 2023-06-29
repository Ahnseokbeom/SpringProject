package page.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import page.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
