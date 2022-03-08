package com.master.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.master.controller.MainController;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	MainController mainController;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(mainController);
	}
}