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
import com.eri.ict.entity.Team;
import com.eri.ict.entity.User;
import com.eri.ict.service.account.AccountService;
import com.eri.ict.service.account.ShiroDbRealm;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.team.ITeamService;
import com.eri.ict.service.team.TeamService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.rambo.common.params.ObjectStatus;

/**
 * Team管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /team/ Create page : GET /team/create Create action : POST
 * /team/create Update page : GET /team/update/{id} Update action : POST
 * /team/update Delete action : GET /team/delete/{id}
 * 
 * @author rambo
 */
@Controller
@RequestMapping(value = "/team")
public class TeamController {

	private static final String PAGE_SIZE = "3";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private static Logger log = LoggerFactory.getLogger(TeamController.class);

	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("name", "Name");
	}

	@Autowired
	private ITeamService teamService;
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Team> teams = teamService.getTeamByPage(searchParams, pageNumber, pageSize, sortType);

		
		model.addAttribute("teams", teams);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "team/teamList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		
		model.addAttribute("users", accountService.getAllUser());
		model.addAttribute("status", ObjectStatus.STATUS);
		model.addAttribute("team", new Team());
		model.addAttribute("action", "create");
		return "team/teamForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Team newTeam, RedirectAttributes redirectAttributes) {
		teamService.saveTeam(newTeam);
		redirectAttributes.addFlashAttribute("message", "Created Team successfully!");
		return "redirect:/team/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("team", teamService.getTeam(id));
		model.addAttribute("action", "update");
		return "team/teamForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("team") Team team, RedirectAttributes redirectAttributes) {
		teamService.saveTeam(team);
		redirectAttributes.addFlashAttribute("message", "Updated Team successfully!");
		return "redirect:/team/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		teamService.deleteTeam(id);
		redirectAttributes.addFlashAttribute("message", "Deleted Team successfully!");
		return "redirect:/team/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Team对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getTeam(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("team", teamService.getTeam(id));
		}
	}

}
