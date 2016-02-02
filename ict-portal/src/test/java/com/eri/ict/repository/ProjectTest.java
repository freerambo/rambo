package com.eri.ict.repository;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.spring.Profiles;

import com.eri.ict.entity.Project;
import com.eri.ict.entity.User;
import com.eri.ict.service.project.IProjectService;
import com.eri.ict.service.project.ProjectService;
import com.google.common.collect.Lists;

public class ProjectTest {

	static ConfigurableApplicationContext ct;
	static ProjectDao dao;
	static ProjectService service;
	static Logger log = LoggerFactory.getLogger(ProjectTest.class);

	public static void main(String[] args) {

		Profiles.setProfileAsSystemProperty(Profiles.PRODUCTION);
		ct = new ClassPathXmlApplicationContext("applicationContext.xml");
		dao = ct.getBean(ProjectDao.class);
		service = ct.getBean(ProjectService.class);
		findAll();
		ct.close();
	}

	static void findAll() {
//		log.info(dao.findAll().toString());
//		log.info("Total numbers --> " + dao.count());

		List<Project> result =  service.getAllProject();
		for(Project p : result){
			log.info(p.getImg());
//			log.info("Leaders " + p.getLeader().getName());

			for(User u : p.getProjectMembers()){
				log.info("members " + u.getName());
			}
		}
		
	}
}