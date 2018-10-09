package com.njkol.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.njkol.cassandra.model.Person;

@Repository
public interface PersonRepository extends CassandraRepository<Person> {

	List<Person> findByKeyFirstName(final String firstName);

	@Query("select * from people_by_first_name where first_name=?0 and date_of_birth=?1 ")
	List<Person> findByNameAndDOB(final String firstName, final String dob);
}