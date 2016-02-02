/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.web.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eri.ict.entity.User;
import com.eri.ict.service.account.AccountService;
import com.eri.ict.service.role.IRoleService;
import com.eri.ict.service.team.ITeamService;
import com.eri.ict.service.team.TeamService;
import com.rambo.common.params.ObjectStatus;

/**
 * 用户注册的Controller.
 * 
 * @author rambo
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private ITeamService teamService;
//	@Autowired
//	private IRoleService roleService;
	@RequestMapping(method = RequestMethod.GET)
	public String registerForm(Model model) {
//		teamService.getAllTeam();
		
		model.addAttribute("teams", teamService.getAllTeam());
//		model.addAttribute("roles", roleService.getAllRole());
		model.addAttribute("status", ObjectStatus.USER_STATUS);

		
		return "account/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid User user, RedirectAttributes redirectAttributes) {
		accountService.registerUser(user);
		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		return "redirect:/login";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		if (accountService.findUserByLoginName(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
}
