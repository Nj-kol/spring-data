package com.njkol.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.njkol.mongo.model.Users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Demonstrating MongoTemplate
 * 
 * @author Nilanjan Sarkar
 *
 */
@RestController
@RequestMapping("/user/v2/")
@Api(value = "Mongo User repo")
public class UsersControllerV2 {

	@Autowired
	private MongoTemplate template;

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Creates a User")
	public void create(@RequestBody Users user) throws Exception {
		template.insert(user);
	}

	@GetMapping("/get/all")
	@ApiOperation(value = "Returns all users", response = List.class)
	public List<Users> getAll() {
		return template.findAll(Users.class);
	}

	@GetMapping("/get/{id}")
	@ApiOperation(value = "Retrives a person by their first id", response = Users.class)
	public Users getPersonById(@PathVariable int id) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return template.findOne(query, Users.class);
	}
}