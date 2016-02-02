/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.web;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eri.ict.entity.Role;
import com.eri.ict.entity.User;
import com.eri.ict.service.account.ShiroDbRealm;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.role.IRoleService;
import com.eri.ict.service.role.RoleService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

/**
 * Role管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /role/ Create page : GET /role/create Create action : POST
 * /role/create Update page : GET /role/update/{id} Update action : POST
 * /role/update Delete action : GET /role/delete/{id}
 * 
 * @author rambo
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {

	private static final String PAGE_SIZE = "3";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private static Logger log = LoggerFactory.getLogger(RoleController.class);

	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("name", "Name");
	}

	@Autowired
	private IRoleService roleService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Role> roles = roleService.getRoleByPage(searchParams, pageNumber, pageSize, sortType);

		
		model.addAttribute("roles", roles);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "role/roleList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("action", "create");
		return "role/roleForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Role newRole, RedirectAttributes redirectAttributes) {

		roleService.saveRole(newRole);
		redirectAttributes.addFlashAttribute("message", "Created Role successfully!");
		return "redirect:/role/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("role", roleService.getRole(id));
		model.addAttribute("action", "update");
		return "role/roleForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
		roleService.saveRole(role);
		redirectAttributes.addFlashAttribute("message", "Updated Role successfully!");
		return "redirect:/role/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		roleService.deleteRole(id);
		redirectAttributes.addFlashAttribute("message", "Deleted Role successfully!");
		return "redirect:/role/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Role对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getRole(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("role", roleService.getRole(id));
		}
	}

}
