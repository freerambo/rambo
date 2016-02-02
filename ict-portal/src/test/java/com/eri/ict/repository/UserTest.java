package com.eri.ict.repository;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.spring.Profiles;

import com.eri.ict.entity.User;
import com.eri.ict.service.account.AccountService;
import com.google.common.collect.Lists;

public class UserTest {

	static ConfigurableApplicationContext ct;
	static UserDao dao;
	static AccountService service;
	static Logger log = LoggerFactory.getLogger(UserTest.class);

	public static void main(String[] args) {

		Profiles.setProfileAsSystemProperty(Profiles.PRODUCTION);
		ct = new ClassPathXmlApplicationContext("applicationContext.xml");
		dao = ct.getBean(UserDao.class);
		service = ct.getBean(AccountService.class);
//		findAll();
		findOne();
		ct.close();
	}

	static void findAll() {
		// log.info(dao.findAll().toString());
		// log.info("Total numbers --> " + dao.count());

		List<User> result = service.getAllUser();
		log.info(result.toString());
	}
	
	static void findOne() {
		// log.info(dao.findAll().toString());
		// log.info("Total numbers --> " + dao.count());

		User result = service.getUser(1L);
		log.info(result.getName());
	}
}