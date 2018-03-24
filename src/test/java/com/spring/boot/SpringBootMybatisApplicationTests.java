package com.spring.boot;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageHelper;
import com.spring.boot.mapper.PeopleMapper;
import com.spring.boot.model.People;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {

	@Resource(name="peopleMapper")
	public PeopleMapper peopleMapper;


	@Test
	public void contextLoads() {
		PageHelper.startPage(1, 5);
		com.github.pagehelper.Page<People> p11 = peopleMapper.queryList(null);
		Assert.assertNotNull(p11);
	}

}
