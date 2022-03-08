package com.slave.node.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.slave.node.controller.MainContoller;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	MainContoller mainController;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(mainController);
	}
}