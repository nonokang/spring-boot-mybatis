package com.spring.boot.utils;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

@Configuration
public class InitConfig {

	/**
	 * <b>描述：</b> 用于处理分页
	 * @author wpk | 2018年3月16日 下午5:31:44 |创建
	 * @return PageHelper
	 */
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();  
        //添加配置，也可以指定文件路径
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
    
}
