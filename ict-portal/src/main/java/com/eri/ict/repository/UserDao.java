/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.repository;



import java.util.List;

import com.eri.ict.entity.User;

public interface UserDao extends BaseDao<User, Long>{

	User findByName(String name);

	User findByLoginName(String loginName);
	
	List<User> findByTeamId(Long teamId);
	
	List<User> findByStatus(String status);
}
