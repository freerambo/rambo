/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.eri.ict.entity.Project;

public interface ProjectDao extends PagingAndSortingRepository<Project, Long>, JpaSpecificationExecutor<Project> {

	Page<Project> findByUserId(Long id, Pageable pageRequest);

	Page<Project> findByCategory(String category, Pageable pageRequest);

	Page<Project> findAll(Pageable pageRequest);

	@Modifying
	@Query("delete from Project project where project.user.id=?1")
	void deleteByUserId(Long id);

}
