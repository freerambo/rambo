/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.service.news;

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
import com.eri.ict.repository.NewsDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.Hibernates;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

// Spring Bean identifier
@Component
// add transaction management for all public functions
@Transactional
public class NewsService implements INewsService {

	private NewsDao projectDao;

	public News getNews(Long id) {
		return projectDao.findOne(id);
	}

	public void saveNews(News entity) {
		projectDao.save(entity);
	}

	public void deleteNews(Long id) {
		projectDao.delete(id);
	}

	public List<News> getAllNews() {

		// return (List<News>) projectDao.findAll();
		List<News> result = (List<News>) projectDao.findAll();
		for (News project : result) {
			// Hibernates.initLazyProperty(project.getNewsMembers());
			// Hibernates.initLazyProperty(project.getLeader());

		}
		return result;

	}

	public Page<News> getNewsByPage(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<News> spec = buildSpecification(searchParams);

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
	 * build the dynamic query Specification
	 */
	private Specification<News> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// filters.put("user.id", new SearchFilter("user.id", Operator.EQ,
		// userId));
		Specification<News> spec = DynamicSpecifications.bySearchFilter(filters.values(), News.class);
		return spec;
	}

	@Autowired
	public void setNewsDao(NewsDao projectDao) {
		this.projectDao = projectDao;
	}
}
