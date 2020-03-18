package com.bridgelabz.fundoonotes.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.fundoonotes.config.InterceptorConfig;

@Component
public class WebAppConfigAdapter implements WebMvcConfigurer {

	@Autowired
	InterceptorConfig userInterceptor;
	
	 @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(userInterceptor);
	   }
}
