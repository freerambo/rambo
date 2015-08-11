/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.rambo.mysql.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.rambo.mysql.entity.City;
@Transactional(value="mysqlEM")
public interface CityDao extends PagingAndSortingRepository<City, Long>, JpaSpecificationExecutor<City> {



}
