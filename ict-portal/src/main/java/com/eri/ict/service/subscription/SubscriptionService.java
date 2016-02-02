/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.service.subscription;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.eri.ict.entity.*;
import com.eri.ict.repository.SubscriptionDao;
import com.rambo.common.utils.email.EmailMessage;
import com.rambo.common.utils.email.MimeMailService;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class SubscriptionService implements ISubscriptionService {

	private SubscriptionDao subscriptionDao;
	private MimeMailService mailService;

	public Subscription getSubscription(Long id) {
		return subscriptionDao.findOne(id);
	}

	public void saveSubscription(Subscription entity) {
		if (null != entity && entity.getEmail() != null) {
			Subscription s = subscriptionDao.findByEmail(entity.getEmail());
			if (null == s) {
				subscriptionDao.save(entity);
			} else {
				entity.setId(s.getId());
			}
			EmailMessage email = new EmailMessage();
			email.setEmail(entity.getEmail());
			email.setSubject("ERIAN ICT Portal Digital Subscription");
			email.setMsg("ERIAN ICT Portal Digital Subscription");
			mailService.sendNotificationMail(email);
		}
	}

	public void deleteSubscription(Long id) {
		subscriptionDao.delete(id);
	}

	public List<Subscription> getAllSubscription() {
		return (List<Subscription>) subscriptionDao.findAll();
	}

	public Page<Subscription> getSubscriptionByPage(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Subscription> spec = buildSpecification(searchParams);

		return subscriptionDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Subscription> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// filters.put("user.id", new SearchFilter("user.id", Operator.EQ,
		// userId));
		Specification<Subscription> spec = DynamicSpecifications.bySearchFilter(filters.values(), Subscription.class);
		return spec;
	}

	@Autowired
	public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
		this.subscriptionDao = subscriptionDao;
	}

	@Autowired
	// @Qualifier("mimeMailService")
	public void setMailService(MimeMailService mailService) {
		this.mailService = mailService;
	}

}
