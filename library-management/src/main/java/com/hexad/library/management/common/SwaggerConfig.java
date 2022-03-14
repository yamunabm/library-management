package com.hexad.library.management.common;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String BASE_PKG = "com.hexad.library.management";

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Value("${team.email.id}")
	private String teamEmailId;

	@Value("${project.service.host}")
	private String serviceHost;

	@Bean
	public Docket swaggerUI() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(BASE_PKG))
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo()).host(serviceHost);
				
	}

	private ApiInfo getApiInfo() {

		return new ApiInfo(Constants.Swagger.PROJECT_NAME + " - " + StringUtils.capitalize(activeProfile),
				Constants.Swagger.PROJECT_DESCRIPTION, appVersion, null,
				new Contact(Constants.Swagger.TEAM_NAME, null, teamEmailId), null, null, Collections.emptyList());
	}
}