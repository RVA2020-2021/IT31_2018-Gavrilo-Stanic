package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Status;
import rva.jpa.Student;
import rva.repository.StudentRepository;

@CrossOrigin
@RestController
@Api(tags = {"Student CRUD Operacije"})
public class StudentRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("student")
	@ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka")
	public Collection<Student> getStudents(){
		return studentRepository.findAll();
	}
	@GetMapping("student/{id}")
	@ApiOperation(value = "Vraća studenta iz baze podataka čija je id-vrednost porsledjena kao path varijabla")
	public Student getStudent(@PathVariable("id") Integer id) {
		return studentRepository.getOne(id);
	}
	@GetMapping("studentIme/{ime}")
	@ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka čije ime sadrži string koji je prosledjen kao path varijabla")
	public Collection<Student> getStudentByIme(@PathVariable("ime") String ime){
		return studentRepository.findByImeContainingIgnoreCase(ime);
	}
	
	@PostMapping("student")
	@ApiOperation(value = "Upisuje studenta u bazu podataka")
	public ResponseEntity<Student> insertStudent(@RequestBody Student student){
		if(!studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Student>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("student")
	@ApiOperation(value = "Modifikuje postojećeg studenta iz baze podataka")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student){
		if(studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping("student/{id}")
	@ApiOperation(value = "Briše sutedenta iz baze podataka čija je id-vrednost prosleđena kao path varijabla")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id){
		if(studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
			if(id == -100) {
				jdbcTemplate.execute(
						"Insert into \"student\"(\"id\", \"ime\", \"prezime\", \"broj_indeksa\", \"status\", \"departman\")"
						+"Values(-100, 'TestImeD', 'TestPrzD','TE100-100', -100, -100)"
					);
			}
			return new ResponseEntity<Student>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		}
	}
	
}
 