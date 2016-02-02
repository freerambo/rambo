package com.rambo.cassandra.test;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.rambo.cassandra.entity.Person;
import com.rambo.cassandra.repository.PersonRepository;

public class Demo {
	static ConfigurableApplicationContext ct = new ClassPathXmlApplicationContext(
			"beans.xml");
	static PersonRepository repository = ct.getBean(PersonRepository.class);
	static Logger log = LoggerFactory.getLogger(Demo.class);

	public static void main(String[] args) {
//		 insert();
//		findAll() ;
//		findByName();
		insertPerson();
		findOne();

		ct.close();
	}

	static void insert() {
		List<Person> persons = Lists.newArrayList();
		for (int i = 0; i < 100; i++) {
			Person person = new Person(Integer.toString(i), "hello" + i,
					10 + new Random().nextInt(30));
			persons.add(person);
		}
		repository.save(persons);
	}

	static void findOne() {
		Person person = repository.findOne("10001");
		log.info(person.toString());
	}
	
	static void findAll() {
		List<Person> persons = (List<Person>) repository.findAll();
		log.info(persons.toString());
	}
	static void findByName() {
		List<Person> persons = repository.findByName("hello504");
		log.info(persons.toString());
	}
	
	static void insertPerson() {
		Person p = new Person("10001","test10001",34);
		p = repository.save(p);
		
		log.info(p.toString());
		
	}
	
}