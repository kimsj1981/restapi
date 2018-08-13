package com.sjkim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({ "local", "dev" })
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.sjkim.controller"))
				.paths(PathSelectors.ant("/api/**")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Rest Api").description("Sjkim Rest Api").version("0.0.1")
				.contact(new Contact("sj.kim", null, "sj0227.kim@nate.co.kr")).build();
	}
}
