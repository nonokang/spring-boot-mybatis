package com.spring.boot.service;

import com.github.pagehelper.Page;
import com.spring.boot.model.People;

public interface PeopleService {

	public People findById(Integer id);
	
	public Page<People> pageList(Integer pageNo, Integer pageSize, People p);
	
	public void save(People bean);
	
	public void update(People bean);
	
	public void opera(String ids, String type);
	
}
