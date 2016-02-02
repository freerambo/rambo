/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.service.uploadFile;

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
import com.eri.ict.repository.UploadFileDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class UploadFileService implements IUploadFileService{

	private UploadFileDao uploadFileDao;

	public UploadFile getUploadFile(Long id) {
		return uploadFileDao.findOne(id);
	}

	public void saveUploadFile(UploadFile entity) {
		uploadFileDao.save(entity);
	}

	public void deleteUploadFile(Long id) {
		uploadFileDao.delete(id);
	}
	
	public void deleteUploadFile(UploadFile file) {
		uploadFileDao.delete(file);
	}

	public List<UploadFile> getAllUploadFile() {
		return (List<UploadFile>) uploadFileDao.findAll();
	}

	public Page<UploadFile> getUploadFileByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<UploadFile> spec = buildSpecification(searchParams);

		return uploadFileDao.findAll(spec, pageRequest);
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
	private Specification<UploadFile> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<UploadFile> spec = DynamicSpecifications.bySearchFilter(filters.values(), UploadFile.class);
		return spec;
	}

	@Autowired
	public void setUploadFileDao(UploadFileDao uploadFileDao) {
		this.uploadFileDao = uploadFileDao;
	}
}
