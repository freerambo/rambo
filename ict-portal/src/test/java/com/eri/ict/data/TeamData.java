/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.eri.ict.data;

import com.eri.ict.entity.*;
import org.springside.modules.test.data.RandomData;

public class TeamData {

	public static Team randomNewTeam() {
		User u = new User();
		u.setId(1L);
		
		Team obj = new Team();
		obj.setMaster(u);
		obj.setName(RandomData.randomName("Team"));
		obj.setStatus("1");

		return obj;
	}
}
