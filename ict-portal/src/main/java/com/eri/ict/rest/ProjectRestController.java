/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.rest;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.eri.ict.entity.Project;
import com.eri.ict.rest.dto.ProjectDTO;
import com.eri.ict.service.project.IProjectService;
import com.google.common.collect.Lists;
import com.rambo.common.utils.ListUtils;

import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;

/**
 * Project-Restful API-Controller.
 * 
 * @author rambo
 */
@RestController
@RequestMapping(value = "/api/v1/project")
public class ProjectRestController {

	private static Logger logger = LoggerFactory.getLogger(ProjectRestController.class);

	@Autowired
	private IProjectService projectService;

	@Autowired
	private Validator validator;

	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<ProjectDTO> list() {

		List<Project> projects = projectService.getAllProject();

		if (ListUtils.isEmpty(projects)) {
			String message = "no any projects exsist!";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		List<ProjectDTO> dtos = Lists.newArrayList();
		for(Project p : projects){
			ProjectDTO dto = BeanMapper.map(p, ProjectDTO.class);
			dto.setContent(null);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
	@RequestMapping(value = "/top{n}",method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<ProjectDTO> top3(@PathVariable("n") int n) {

		List<Project> projects = projectService.getTopN(n);

		if (ListUtils.isEmpty(projects)) {
			String message = "no any projects exsist!";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		List<ProjectDTO> dtos = Lists.newArrayList();
		for(Project p : projects){
			ProjectDTO dto = BeanMapper.map(p, ProjectDTO.class);
			dto.setContent(null);
			dtos.add(dto);
		}
		
		return dtos;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ProjectDTO get(@PathVariable("id") Long id) {
		Project project = projectService.getProject(id);
		if (project == null) {
			String message = "Project doesn't exist (id:" + id + ")";
			logger.warn(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		// 使用Dozer转换DTO类，并补充Dozer不能自动绑定的属性
		ProjectDTO dto = BeanMapper.map(project, ProjectDTO.class);
//		dto.setType(ObjectStatus.PROJECT_TYPES.get(project.getStatus()));
		return dto;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> create(@RequestBody Project project, UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, project);

		// 保存任务
		projectService.saveProject(project);

		// 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = project.getId();
		URI uri = uriBuilder.path("/api/v1/project/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	// 按Restful风格约定，返回204状态码, 无内容. 也可以返回200状态码.
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Project project) {
		// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, project);

		// 保存任务
		projectService.saveProject(project);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		projectService.deleteProject(id);
	}
}
