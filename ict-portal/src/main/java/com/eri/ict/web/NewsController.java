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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eri.ict.entity.News;
import com.eri.ict.entity.User;
import com.eri.ict.service.account.ShiroDbRealm;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.news.INewsService;
import com.eri.ict.service.news.NewsService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.rambo.common.params.ObjectStatus;
import com.rambo.common.utils.ListUtils;

/**
 * News management Controller, with Restful Urls:
 * 
 * List page : GET /news/ Create page : GET /news/create Create
 * action : POST /news/create Update page : GET
 * /news/update/{id} Update action : POST /news/update Delete
 * action : GET /news/delete/{id}
 * 
 * @author rambo
 */
@Controller
@RequestMapping(value = "/news")
public class NewsController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private static Logger log = LoggerFactory.getLogger(NewsController.class);

	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("name", "Name");
	}

	@Autowired
	private INewsService newsService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<News> newss = newsService.getNewsByPage(searchParams, pageNumber, pageSize,
				sortType);


		model.addAttribute("types", ObjectStatus.TYPES);
		model.addAttribute("newss", newss);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "news/newsList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		
		
		model.addAttribute("news", new News());
		model.addAttribute("action", "create");
		model.addAttribute("types", ObjectStatus.TYPES);
		return "news/newsForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid News newNews, RedirectAttributes redirectAttributes) {
		

		newsService.saveNews(newNews);
		redirectAttributes.addFlashAttribute("message", "Created News successfully!");
		return "redirect:/news/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		News news = newsService.getNews(id);
		model.addAttribute("news", news);
		Map<String, String> types = Maps.newLinkedHashMap(ObjectStatus.TYPES);
		if (news != null){
			model.addAttribute("type", ObjectStatus.TYPES.get(news.getType()));
			types.remove(news.getType());
			
		}
		model.addAttribute("types", types);

		model.addAttribute("action", "update");
		return "news/newsForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("news") News news,
			RedirectAttributes redirectAttributes) {
		log.info("update news ---> publisher " + getCurrentUserId());
		news.setPublisher(new User(getCurrentUserId()));
		newsService.saveNews(news);
		redirectAttributes.addFlashAttribute("message", "Updated News successfully!");
		return "redirect:/news/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		newsService.deleteNews(id);
		redirectAttributes.addFlashAttribute("message", "Deleted News successfully!");
		return "redirect:/news/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出News对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getNews(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("news", newsService.getNews(id));
		}
	}
	
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	

}
