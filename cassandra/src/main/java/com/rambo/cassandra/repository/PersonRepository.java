package com.rambo.cassandra.repository;

import org.springframework.data.repository.CrudRepository;

import com.rambo.cassandra.entity.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

}