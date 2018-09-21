package com.njkol.mongo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.njkol.mongo.model.Users;
import com.njkol.mongo.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value = "Mongo User repo")
public class UsersController {

	private UserRepository userRepository;

	public UsersController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a User")
	public void create(@RequestBody Users user) throws Exception {
		userRepository.save(user);
	}

	@GetMapping("/get/all")
	@ApiOperation(value = "Returns all users", response = List.class)
	public List<Users> getAll() {
		return userRepository.findAll();
	}

	@GetMapping("/get/{id}")
	@ApiOperation(value = "Retrives a person by their first id", response = Users.class)
	public Users getPersonById(@PathVariable int id) throws Exception {
		return userRepository.findOne(id);
	}
}