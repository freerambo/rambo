/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.utilities.email;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.eri.ict.entity.EmailMessage;
import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * MIME邮件服务类.
 * 
 * 演示由Freemarker引擎生成的的html格式邮件, 并带有附件.
 * 
 * @author rambo
 */
public class MimeMailService {

	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

	private JavaMailSender mailSender;

	private Template template;

	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public String sendNotificationMail(EmailMessage email) {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

//			helper.setTo("zhuyb@ntu.edu.sg");
			helper.setTo(email.getEmail());

//			helper.setFrom("springside3.demo@gmail.com");
			helper.setSubject("用户修改通知");
//			EmailMessage email =  new EmailMessage("scott", "zhuyb@ntu.edu.sg", "I love you", "+65-8888-8888", new Date());
//			logger.info(email.toString());

			String content = generateContent(email);
			helper.setText(content, true);

//			File attachment = generateAttachment();
//			helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至" + email.getEmail());
			return "HTML版邮件已发送成功";
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
			return "构造邮件失败";
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
			return "发送邮件失败";

		}
	}

	public void sendNotificationMail_bak(String userName) {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

			helper.setTo("zhuyb@ntu.edu.sg");
//			helper.setFrom("springside3.demo@gmail.com");
			helper.setSubject("用户修改通知");
//			EmailMessage email =  new EmailMessage("scott", "zhuyb@ntu.edu.sg", "I love you", "+65-8888-8888", "2015-03-05 08:27:36");
//			logger.info(email.toString());
			EmailMessage email =  new EmailMessage("scott", "zhuyb@ntu.edu.sg", "I love you", "+65-8888-8888", new Date());

			String content = generateContent(email);
			helper.setText(content, true);

//			File attachment = generateAttachment();
//			helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至" + email.getEmail());
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}
	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent(EmailMessage email) throws MessagingException {

		try {
			logger.info(email.getName());
			Map<String,Object> context = Maps.newConcurrentMap();
			context.put("userName", email.getName());
			context.put("email", email.getEmail());
			context.put("phone", email.getPhone());
			context.put("msg", email.getMsg());
			context.put("createDate", email.getCreateDate().toString());

			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 获取classpath中的附件.
	 */
	private File generateAttachment() throws MessagingException {
		try {
			Resource resource = new ClassPathResource("/email/mailAttachment.txt");
			return resource.getFile();
		} catch (IOException e) {
			logger.error("构造邮件失败,附件文件不存在", e);
			throw new MessagingException("附件文件不存在", e);
		}
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		// 根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
	}
}
