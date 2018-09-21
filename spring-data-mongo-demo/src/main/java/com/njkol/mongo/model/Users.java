package com.njkol.mongo.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(notes = "User ID")
	private Integer id;
	@ApiModelProperty(notes = "Name of the user")
	private String name;
	@ApiModelProperty(notes = "Name of the user team")
	private String teamName;
	@ApiModelProperty(notes = "Salary of the user")
	private Long salary;

	public Users(Integer id, String name, String teamName, Long salary) {
		this.id = id;
		this.name = name;
		this.teamName = teamName;
		this.salary = salary;
	}

	public Users() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}
}