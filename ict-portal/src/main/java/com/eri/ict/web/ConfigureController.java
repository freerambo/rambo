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
import com.eri.ict.service.account.ShiroDbRealm;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.team.ITeamService;
import com.eri.ict.service.team.TeamService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

/**
 * 
 * @description ConfigureController
 * @version currentVersion(1.0)
 * @author Yuanbo Zhu
 * @createtime Nov 27, 2015 3:40:28 PM
 */
@Controller
public class ConfigureController {

	@RequestMapping(value="/configure",method = RequestMethod.GET)
	public String configure() {
		return "configure";
	}
	
	@RequestMapping(value="/assets",method = RequestMethod.GET)
	public String assets() {
		return "configure";
	}

}
