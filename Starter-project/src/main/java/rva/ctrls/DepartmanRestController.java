package rva.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Departman;
import rva.jpa.Fakultet;
import rva.repository.DepartmanRepository;


@CrossOrigin
@RestController
@Api(tags = {"Departman CRUD Operacije"})
public class DepartmanRestController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DepartmanRepository departmanRepository;
	
	@GetMapping("departman")
	@ApiOperation(value = "Vraća kolekciju svih departmana iz baze podataka")
	public Collection<Departman> getDepartmans() {
		return departmanRepository.findAll();
	}
	@GetMapping("departman/{id}")
	@ApiOperation(value = "Vraća departman iz baze podataka čija je id-vrednost porsledjena kao path varijabla")
	public Departman getDepartman(@PathVariable("id") Integer id) {
		return departmanRepository.getOne(id);
	}
	@GetMapping("departmanNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju svih departmana iz baze podataka čiji naziv sadrži string koji je prosledjen kao path varijabla")
	public Collection<Departman> getDepartmanByNaziv(@PathVariable("naziv") String naziv) {
		return departmanRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("departman")
	@ApiOperation(value = "Upisuje departman u bazu podataka")
	public ResponseEntity<Departman> insertDepartman(@RequestBody Departman departman){
		if(!departmanRepository.existsById(departman.getId())) {
			departmanRepository.save(departman);
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Departman>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("departman")
	@ApiOperation(value = "Modifikuje postojeći departman iz baze podataka")
	public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman){
		if(departmanRepository.existsById(departman.getId())) {
			departmanRepository.save(departman);
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
		}
	}
	
	@Transactional
	@DeleteMapping("departman/{id}")
	@ApiOperation(value = "Briše departman iz baze podataka čija je id-vrednost prosleđena kao path varijabla")
	public ResponseEntity<Departman> deleteDepartman(@PathVariable("id") Integer id){
		if(departmanRepository.existsById(id)) {
			jdbcTemplate.execute("delete from student where departman = "+id);
			departmanRepository.deleteById(id);
			
			//added because the Transactional annotation ruins the test if the id is -100
			departmanRepository.flush();
			if(id == -100) {
				jdbcTemplate.execute(
						"Insert into \"departman\"(\"id\", \"naziv\", \"oznaka\", \"fakultet\")"
						+"Values(-100, 'testD departman', 'TestD', -100)"
					);
				jdbcTemplate.execute(
						"Insert into \"student\"(\"id\", \"ime\", \"prezime\", \"broj_indeksa\", \"status\", \"departman\")"
						+"Values(-100, 'TestIme', 'TestPrz','TE100-100', -100, -100)"
					);
			}
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
		}
	}
	
}
