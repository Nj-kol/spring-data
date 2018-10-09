package com.njkol.cassandra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.njkol.cassandra.model.Person;
import com.njkol.cassandra.repository.PersonRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller class for person entity
 * 
 * @author Nilanjan Sarkar
 *
 */
@RestController
@RequestMapping("/person")
@Api(value = "Person Service")
public class PersonController {

	private PersonRepository personRepository;

	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a Person")
	public void create(@RequestBody Person user) throws Exception {
		personRepository.save(user);
	}

	@GetMapping("/get/{name}")
	@ApiOperation(value = "Retrives a person by their first name", response = List.class)
	public List<Person> getPersonByName(@PathVariable String name) throws Exception {
		return personRepository.findByKeyFirstName(name);
	}
	

	@GetMapping("/get/{name}/{dob}")
	@ApiOperation(value = "Retrives a person by their first name and date of birth", response = List.class)
	public List<Person> getPersonByFirtNameAndDOB(@PathVariable String name,@PathVariable String dob) throws Exception {
		return personRepository.findByNameAndDOB(name,dob);
	}
}
