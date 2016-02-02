/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eri.ict.entity.UploadFile;
import com.eri.ict.entity.User;
import com.eri.ict.rest.RestException;
import com.eri.ict.service.account.ShiroDbRealm;
import com.eri.ict.service.account.ShiroDbRealm.ShiroUser;
import com.eri.ict.service.uploadFile.IUploadFileService;
import com.eri.ict.service.uploadFile.UploadFileService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.rambo.common.params.FileConstants;
import com.rambo.common.params.ObjectStatus;

/**
 * UploadFile管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /uploadFile/ Create page : GET /uploadFile/create Create
 * action : POST /uploadFile/create Update page : GET /uploadFile/update/{id}
 * Update action : POST /uploadFile/update Delete action : GET
 * /uploadFile/delete/{id}
 * 
 * @author rambo
 */
@Controller
@RequestMapping(value = "/uploadFile")
public class UploadFileController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	private static Logger log = LoggerFactory.getLogger(UploadFileController.class);

	static {
		sortTypes.put("auto", "Auto");
		sortTypes.put("name", "Name");
	}

	@Autowired
	private IUploadFileService uploadFileService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<UploadFile> uploadFiles = uploadFileService.getUploadFileByPage(searchParams, pageNumber, pageSize,
				sortType);

		model.addAttribute("uploadFiles", uploadFiles);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		
		return "uploadFile/uploadFileList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("uploadFile", new UploadFile());
		model.addAttribute("action", "create");
		return "uploadFile/uploadFileForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid UploadFile newUploadFile, RedirectAttributes redirectAttributes) {

		uploadFileService.saveUploadFile(newUploadFile);
		redirectAttributes.addFlashAttribute("message", "Created UploadFile successfully!");
		return "redirect:/uploadFile/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("uploadFile", uploadFileService.getUploadFile(id));
		model.addAttribute("action", "update");
		return "uploadFile/uploadFileForm";
	}

	@RequestMapping(value = { "view/{id}", "download/{id}" }, method = RequestMethod.GET)
	public void viewFile(@NotNull @PathVariable("id") Long id, HttpServletResponse response, Model model)
			throws IOException {
		response.setCharacterEncoding("UTF-8");

		UploadFile uploadFile = uploadFileService.getUploadFile(id);
		if (uploadFile == null) {
			String message = "Can't find the file (id:" + id + ")";
			log.warn("message");
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		log.info(FileConstants.UPLOAD_PATH + File.separator + uploadFile.getName());
		File serverFile = new File(FileConstants.UPLOAD_PATH + File.separator + uploadFile.getName());

		if (!serverFile.exists()) {
			String message = "File does not exist (id:" + uploadFile.getId() + ", name: " + uploadFile.getName() + ")";
			log.warn("message");
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}

		response.setContentType(uploadFile.getType());
		if (uploadFile.getType().contains("application")) {

			/*
			 * "Content-Disposition : attachment" will be directly download, may
			 * provide save as popup, based on your browser setting
			 */
			response.setHeader("Content-Disposition",
					String.format("attachment;filename=\"%s\"", uploadFile.getName()));
		} else {
			/*
			 * "Content-Disposition : inline" will show viewable types [like
			 * images/text/pdf/anything viewable by browser] right on browser
			 * while others(zip e.g) will be directly downloaded [may provide
			 * save as popup, based on your browser setting.]
			 */
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + serverFile.getName() + "\""));
			response.setHeader("Content-Type", String.format("inline; filename=\"" + serverFile.getName() + "\""));
		}

		response.setContentLength((int) serverFile.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(serverFile));

		// Copy bytes from source to destination(outputstream in this example),
		// closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());

	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("uploadFile") UploadFile uploadFile,
			RedirectAttributes redirectAttributes) {
		uploadFileService.saveUploadFile(uploadFile);
		redirectAttributes.addFlashAttribute("message", "Updated UploadFile successfully!");
		return "redirect:/uploadFile/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {

		String message = "Deleted UploadFile successfully!";

		UploadFile uploadFile = uploadFileService.getUploadFile(id);
		if (uploadFile == null) {
			message = "The file doesn't exist in DB (id:" + id + ")";
			// log.warn("message");
			// throw new RestException(HttpStatus.NOT_FOUND, message);
		} else {
			File serverFile = new File(FileConstants.UPLOAD_PATH + File.separator + uploadFile.getName());
			log.info(serverFile.getAbsolutePath());
			if (!serverFile.exists()) {
				message = "File does not exist (id:" + uploadFile.getId() + ", name: " + uploadFile.getName() + ")";
				log.warn("message");
			} else {
				message += "File --> " + serverFile.getName();
				serverFile.delete();
				log.warn(message);
			}

			uploadFileService.deleteUploadFile(uploadFile);
		}
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/uploadFile/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出UploadFile对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUploadFile(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("uploadFile", uploadFileService.getUploadFile(id));
		}
	}

}
