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
import org.hibernate.validator.constraints.Email;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eri.ict.entity.News;
import com.eri.ict.entity.Subscription;
import com.eri.ict.entity.User;
import com.eri.ict.service.account.ShiroDbRealm;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.subscription.ISubscriptionService;
import com.eri.ict.service.subscription.SubscriptionService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.rambo.common.params.ObjectStatus;
import com.rambo.common.utils.ListUtils;

/**
 * Subscription管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /subscription/ Create page : GET /subscription/create Create
 * action : POST /subscription/create Update page : GET
 * /subscription/update/{id} Update action : POST /subscription/update Delete
 * action : GET /subscription/delete/{id}
 * 
 * @author rambo
 */
@Controller
@RequestMapping(value = "/subscription")
public class SubscriptionController {

	private static final String PAGE_SIZE = "3";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private static Logger log = LoggerFactory.getLogger(SubscriptionController.class);

	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("name", "Name");
	}

	@Autowired
	private ISubscriptionService subscriptionService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Subscription> subscriptions = subscriptionService.getSubscriptionByPage(searchParams, pageNumber, pageSize,
				sortType);

		model.addAttribute("types", ObjectStatus.TYPES);

		model.addAttribute("subscriptions", subscriptions);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "subscription/subscriptionList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("subscription", new Subscription());
		model.addAttribute("action", "create");
		model.addAttribute("types", ObjectStatus.TYPES);

		return "subscription/subscriptionForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Subscription newSubscription, RedirectAttributes redirectAttributes) {

		subscriptionService.saveSubscription(newSubscription);
		redirectAttributes.addFlashAttribute("message", "Created Subscription successfully!");
		return "redirect:/subscription/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {

		Subscription subscription = subscriptionService.getSubscription(id);
		model.addAttribute("subscription", subscription);
		Map<String, String> types = Maps.newLinkedHashMap(ObjectStatus.TYPES);
		if (subscription != null) {
			model.addAttribute("type", ObjectStatus.TYPES.get(subscription.getType()));
			types.remove(subscription.getType());

		}
		model.addAttribute("types", types);

		model.addAttribute("action", "update");
		return "subscription/subscriptionForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("subscription") Subscription subscription,
			RedirectAttributes redirectAttributes) {
		subscriptionService.saveSubscription(subscription);
		redirectAttributes.addFlashAttribute("message", "Updated Subscription successfully!");
		return "redirect:/subscription/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		subscriptionService.deleteSubscription(id);
		redirectAttributes.addFlashAttribute("message", "Deleted Subscription successfully!");
		return "redirect:/subscription/";
	}

	

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Subscription对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getSubscription(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("subscription", subscriptionService.getSubscription(id));
		}
	}

}
