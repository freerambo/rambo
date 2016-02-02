/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.service.project;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.eri.ict.entity.*;
import com.eri.ict.repository.ProjectDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.Hibernates;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

// Spring Bean identifier
@Component
// add transaction management for all public functions
@Transactional
public class ProjectService implements IProjectService {

	private ProjectDao projectDao;

	public Project getProject(Long id) {
		return projectDao.findOne(id);
	}

	public void saveProject(Project entity) {
		projectDao.save(entity);
	}

	public void deleteProject(Long id) {
		projectDao.delete(id);
	}

	public List<Project> getAllProject() {

		// return (List<Project>) projectDao.findAll();
		List<Project> result = (List<Project>) projectDao.findAll();
		for (Project project : result) {
//			Hibernates.initLazyProperty(project.getProjectMembers());
//			Hibernates.initLazyProperty(project.getLeader());

		}
		return result;

	}

	public Page<Project> getProjectByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Project> spec = buildSpecification(searchParams);

		return projectDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Project> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// filters.put("user.id", new SearchFilter("user.id", Operator.EQ,
		// userId));
		Specification<Project> spec = DynamicSpecifications.bySearchFilter(filters.values(), Project.class);
		return spec;
	}

	@Autowired
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	/*  
	 (non-Javadoc)  
	* @see com.eri.ict.service.project.IProjectService#getTopN(int)  
	*/
		
	@Override
	public List<Project> getTopN(int n) {
		// TODO Auto-generated method stub
		return projectDao.getTopN(n);
	}
}
