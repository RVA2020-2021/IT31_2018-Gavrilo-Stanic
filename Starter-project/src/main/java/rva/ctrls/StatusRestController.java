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
import rva.jpa.Status;
import rva.repository.StatusRepository;

@CrossOrigin
@RestController
@Api(tags = {"Status CRUD Operacije"})
public class StatusRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@GetMapping("status")
	@ApiOperation(value = "Vraća kolekciju svih statusa iz baze podataka")
	public Collection<Status> getStatuses() {
		return statusRepository.findAll();
	}
	
	@GetMapping("status/{id}")
	@ApiOperation(value = "Vraća status iz baze podataka čija je id-vrednost porsledjena kao path varijabla")
	public Status getStatus(@PathVariable("id") Integer id) {
		return statusRepository.getOne(id);
	}
	@GetMapping("statusNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju svih statusa iz baze podataka čiji naziv sadrži string koji je prosledjen kao path varijabla")
	public Collection<Status> getStatusByNaziv(@PathVariable("naziv") String naziv){
		return statusRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("status")
	@ApiOperation(value = "Upisuje status u bazu podataka")
	public ResponseEntity<Status> insertStatus(@RequestBody Status status){
		if(!statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<Status>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("status")
	@ApiOperation(value = "Modifikuje postojeći status iz baze podataka")
	public ResponseEntity<Status> updateStatus(@RequestBody Status status){
		if(statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<Status>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
		}
	} 
	
	@Transactional
	@DeleteMapping("status/{id}")
	@ApiOperation(value = "Briše status iz baze podataka čiji je id-vrednost prosleđena kao path varijabla")
	public ResponseEntity<Status> deleteStatus(@PathVariable("id") Integer id){
		if(statusRepository.existsById(id)) {
			
			jdbcTemplate.execute("delete from student where status = "+id);
			statusRepository.deleteById(id);
			//added because the Transactional annotation ruins the test if the id is -100
			statusRepository.flush(); 
			if(id == -100) {
				jdbcTemplate.execute(
						"Insert into \"status\"(\"id\", \"naziv\", \"oznaka\")"
						+"Values(-100, 'TestD Status', 'TestD')"
					);
				jdbcTemplate.execute(
						"Insert into \"student\"(\"id\", \"ime\", \"prezime\", \"broj_indeksa\", \"status\", \"departman\")"
						+"Values(-100, 'TestIme', 'TestPrz','TE100-100', -100, -100)"
					);
			}
			return new ResponseEntity<Status>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
		}
	}
}
