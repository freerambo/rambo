/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eri.ict.entity.Project;
import com.eri.ict.entity.User;
import com.eri.ict.service.account.AccountService;
import com.eri.ict.service.account.ShiroDbRealm;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.project.IProjectService;
import com.eri.ict.service.project.ProjectService;

import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Collections3;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.rambo.common.params.ObjectStatus;
import com.rambo.common.utils.ListUtils;

/**
 * Project管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /project/ Create page : GET /project/create Create action :
 * POST /project/create Update page : GET /project/update/{id} Update action :
 * POST /project/update Delete action : GET /project/delete/{id}
 * 
 * @author rambo
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectController {

	private static final String PAGE_SIZE = "6";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private static Logger log = LoggerFactory.getLogger(ProjectController.class);

	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("name", "Name");
	}

	@Autowired
	private IProjectService projectService;
	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Project> projects = projectService.getProjectByPage(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("types", ObjectStatus.PROJECT_TYPES);

		model.addAttribute("projects", projects);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "project/projectList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("project", new Project());
		model.addAttribute("action", "create");

		model.addAttribute("users", accountService.getAllUser());
		model.addAttribute("types", ObjectStatus.PROJECT_TYPES);
		return "project/projectForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Project newProject, RedirectAttributes redirectAttributes) {

		// log.info(newProject.getContent());
		newProject.setStatus("1");
		newProject.setCreateDate(Clock.DEFAULT.getCurrentDate());
		projectService.saveProject(newProject);
		redirectAttributes.addFlashAttribute("message", "Created Project successfully!");
		return "redirect:/project/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		Project p = projectService.getProject(id);

		List<User> users = accountService.getAllUser();

		Map<String, String> types = Maps.newLinkedHashMap(ObjectStatus.PROJECT_TYPES);
		if (p != null) {
			model.addAttribute("type", ObjectStatus.PROJECT_TYPES.get(p.getType()));
			types.remove(p.getType());

		}
		model.addAttribute("types", types);

		model.addAttribute("project", p);
		model.addAttribute("action", "update");
		model.addAttribute("users", users);

		return "project/projectForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("project") Project project, BindingResult result,
			RedirectAttributes redirectAttributes) {

		projectService.saveProject(project);
		redirectAttributes.addFlashAttribute("message", "Updated Project successfully!");
		return "redirect:/project/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		projectService.deleteProject(id);
		redirectAttributes.addFlashAttribute("message", "Deleted Project successfully!");
		return "redirect:/project/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Project对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getProject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("project", projectService.getProject(id));
		}
	}

	/**
	 * 
	 * @param binder
	 * @description bind the projectMembers to List<User>
	 * 
	 *              http://stackoverflow.com/questions/4331532/multiple-select-
	 *              in-spring-3-0-mvc
	 * @version currentVersion
	 * @author Yuanbo Zhu
	 * @createtime Nov 24, 2015 9:18:10 PM
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(List.class, "projectMembers", new CustomCollectionEditor(List.class) {
			@Override
			protected Object convertElement(Object element) {
				Long id = null;

				if (element instanceof String && !((String) element).equals("")) {
					// From the JSP 'element' will be a String
					try {
						id = Long.parseLong((String) element);
					} catch (NumberFormatException e) {
						System.out.println("Element was " + ((String) element));
						e.printStackTrace();
					}
				} else if (element instanceof Long) {
					// From the database 'element' will be a Long
					id = (Long) element;
				}

				return id != null ? new User(id) : null;
			}
		});
	}
}
