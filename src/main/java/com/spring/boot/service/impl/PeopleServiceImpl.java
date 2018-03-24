package com.spring.boot.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spring.boot.mapper.PeopleMapper;
import com.spring.boot.model.People;
import com.spring.boot.service.PeopleService;

@Service(value="peopleService")
public class PeopleServiceImpl implements PeopleService{

	@Resource(name="peopleMapper")
	public PeopleMapper peopleMapper;

	@Override
	public People findById(Integer id) {
		return peopleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<People> pageList(Integer pageNo, Integer pageSize, People p) {
		PageHelper.startPage(pageNo, pageSize);
		Page<People> page = peopleMapper.queryList(p);
		return page;
	}

	@Override
	public void save(People bean) {
		peopleMapper.insert(bean);
	}

	@Override
	public void update(People bean) {
		peopleMapper.updateByPrimaryKey(bean);
	}

	@Override
	public void opera(String ids, String type) {
		if(null != ids && ids.length() != 0){
			String[] _ids = ids.split(",");
			for(String id : _ids){
				if(null == id || "".equals(id)) continue;
				People p = findById(Integer.parseInt(id));
				p.setOpera(type);
				update(p);
			}
		}else{
			throw new NullPointerException(String.format("参数id为空！"));
		}
	}
}
