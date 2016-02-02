package com.rambo.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;



import com.rambo.cassandra.entity.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

	@Query("select * from person where name=?0")
	List<Person> findByName(String name);
}