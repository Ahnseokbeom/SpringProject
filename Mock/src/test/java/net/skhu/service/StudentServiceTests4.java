package net.skhu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import net.skhu.dto.Student;
import net.skhu.mapper.StudentMapper;

@SpringBootTest
public class StudentServiceTests4 {
	@Mock
	StudentMapper studentMapper;
	@InjectMocks
	StudentService studentService;
	Student student;

	@BeforeEach public void prepare() {
		this.student = new Student();
		this.student.setId(337);
		this.student.setStudentNo("201132011");
		this.student.setName("임꺽정");
		this.student.setDepartmentId(2);
		this.student.setPhone("010-123-4567");
		this.student.setEmail("lim@skhu.ac.kr");
	}

	@Test 
	public void test_insert_OK() { 
		Mockito.when(studentMapper.findByStudentNo(ArgumentMatchers.anyString())) 
		.thenReturn(null);
		studentService.insert(this.student);
		Mockito.verify(studentMapper).insert(this.student);
	}

	@Test
	public void test_insert_학번중복() { 
		Mockito.when(studentMapper.findByStudentNo(ArgumentMatchers.anyString())) 
		.thenReturn(this.student); 
		studentService.insert(this.student);
		Mockito.verify(studentMapper, Mockito.times(0)).insert(ArgumentMatchers.any());
	}
	

}
