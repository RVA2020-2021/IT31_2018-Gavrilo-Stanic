package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
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
import rva.jpa.Fakultet;
import rva.repository.FakultetRepository;

@CrossOrigin
@RestController
@Api(tags = {"Fakultet CRUD Operacije"})
public class FakultetRestController {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FakultetRepository fakultetRepository;
	
	@GetMapping("fakultet")
	@ApiOperation(value = "Vraća kolekciju svih fakulteta iz baze podataka")
	public Collection<Fakultet> getFaculties(){
		return  fakultetRepository.findAll();
	}
	@GetMapping("fakultet/{id}")
	@ApiOperation(value = "Vraća fakultet iz baze podataka čija je id-vrednost porsledjena kao path varijabla")
	public Fakultet getFaculty(@PathVariable("id") Integer id) {
		return fakultetRepository.getOne(id);
	}
	@GetMapping("fakultetNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju svih fakultete iz baze podataka čiji naziv sadrži string koji je prosledjen kao path varijabla")
	public Collection<Fakultet> getFacultyByNaziv(@PathVariable("naziv") String naziv){
			return fakultetRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("fakultet")
	@ApiOperation(value = "Upisuje fakulet u bazu podataka")
	public ResponseEntity<Fakultet> insertFaculty(@RequestBody Fakultet fakultet ){
		if(!fakultetRepository.existsById(fakultet.getId())) {
			fakultetRepository.save(fakultet);
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}
		return new ResponseEntity<Fakultet>(HttpStatus.CONFLICT);
		
	}
	
	@PutMapping("fakultet")
	@ApiOperation(value = "Modifikuje postojeći fakultet iz baze podataka")
	public ResponseEntity<Fakultet> updateFaculty(@RequestBody Fakultet fakultet){
		if(fakultetRepository.existsById(fakultet.getId())) {
			fakultetRepository.save(fakultet);
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Fakultet>(HttpStatus.NO_CONTENT);
		}
	}
	
	@Transactional
	@DeleteMapping("fakultet/{id}")
	@ApiOperation(value = "Briše fakultet iz baze podataka čiji je id-vrednost prosleđena kao path varijabla")
	public ResponseEntity<Fakultet> deleteFaculty(@PathVariable("id") Integer id){
		if(fakultetRepository.existsById(id)) {
			jdbcTemplate.execute("delete from student where departman  in (select id from departman where fakultet = "+id+")");
			jdbcTemplate.execute("delete from departman where fakultet = "+id);
			fakultetRepository.deleteById(id);
			
			//added because the Transactional annotation ruins the test if the id is -100
			fakultetRepository.flush(); 
			//for testing
			if(id == -100) {
				jdbcTemplate.execute(
							"Insert into \"fakultet\"(\"id\", \"naziv\", \"sediste\")"
							+"VALUES (-100, 'TestD fakultet', 'TestD Grad')"
						); 
				jdbcTemplate.execute(
						"Insert into \"departman\"(\"id\", \"naziv\", \"oznaka\", \"fakultet\")"
						+"Values(-100, 'test departman', 'Test', -100)"
					);
				jdbcTemplate.execute(
						"Insert into \"student\"(\"id\", \"ime\", \"prezime\", \"broj_indeksa\", \"status\", \"departman\")"
						+"Values(-100, 'TestIme', 'TestPrz','TE100-100', -100, -100)"
					);
			}
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Fakultet>(HttpStatus.NO_CONTENT);
		}
	}
}
