package com.eri.ict.repository;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.spring.Profiles;

import com.eri.ict.data.UserData;
import com.eri.ict.entity.*;
import com.google.common.collect.Lists;

public class UserDaoTest {

	static ConfigurableApplicationContext ct;
	static UserDao dao;
	static Logger log = LoggerFactory.getLogger(UserDaoTest.class);

	public static void main(String[] args) {

		Profiles.setProfileAsSystemProperty(Profiles.PRODUCTION);
		ct = new ClassPathXmlApplicationContext("applicationContext.xml");
		dao = ct.getBean(UserDao.class);
//		findAll();
		create();
		ct.close();
	}

	static void findAll() {
		log.info(dao.findAll().toString());
		log.info("Total numbers --> " + dao.count());

	}

	static void create() {
		User u = UserData.randomNewUser();
		log.info(u.toString());
		log.info(dao.save(u).toString());
		log.info("Total numbers --> " + dao.count());

	}

}