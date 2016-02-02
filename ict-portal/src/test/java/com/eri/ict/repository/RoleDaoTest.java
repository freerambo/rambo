package com.eri.ict.repository;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.spring.Profiles;

import com.google.common.collect.Lists;

public class RoleDaoTest {

	
	static ConfigurableApplicationContext ct;
	static RoleDao dao;
	static Logger log = LoggerFactory.getLogger(RoleDaoTest.class);

	public static void main(String[] args) {
		
		Profiles.setProfileAsSystemProperty(Profiles.PRODUCTION);
		ct = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		dao = ct.getBean(RoleDao.class);
		findAll();
		ct.close();
	}

	static void findAll() {
		log.info(dao.findAll().toString());
		log.info("Total numbers --> "+dao.count());
		
	}	
}