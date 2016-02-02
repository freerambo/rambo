/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springside.modules.utils.Clock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

//JPA标识
@Entity
@Table(name = "t_project")
public class Project extends IdEntity {

	private String title;
	private String description;
	private String content;
	private String status;
	private String type;
	private Date createDate;

	private String img;
//	private User leader;

	private List<User> projectMembers = Lists.newArrayList(); // 有序的关联对象集合

	
	// 多对多定义
	@ManyToMany
	@JoinTable(name = "t_project_user", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略	
	public List<User> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(List<User> projectMembers) {
		this.projectMembers = projectMembers;
	}

	
	// JSR303 BeanValidator的校验规则
	@NotBlank
	public String getTitle() {
		return title;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

/*	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "leader")
	public User getLeader() {
		return leader;
	}

	public void setLeader(User user) {
		this.leader = user;
	}*/

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
