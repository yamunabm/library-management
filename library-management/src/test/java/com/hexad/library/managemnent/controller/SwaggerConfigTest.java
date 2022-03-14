package com.hexad.library.managemnent.controller;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.hexad.library.management.common.SwaggerConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;

import springfox.documentation.spring.web.plugins.Docket;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {

	@Test
	void testSwaggerUI() {
		final Docket docket = new SwaggerConfig().swaggerUI();
		assertNotNull(docket);
	}

	@Test
	@Disabled
	public void shouldReturnApiDocs() {
		ValidatableResponse then = get("http://localhost:8080/v2/api-docs").then();
		then.body("host", equalTo("localhost:8080"));
		then.body("tags.size()", equalTo(2));
		then.body("paths.size()", equalTo(8));
	}

	@Test
	@Disabled
	public void shouldReturnSucessHttpstatus() {
		Response response = get("http://localhost:8080/v2/api-docs").thenReturn();

		assertTrue(response.getStatusCode() == HttpStatus.SC_OK);
		assertNotNull(response.getBody());

	}

	@EnableAutoConfiguration
	public static class SomeApplication {
	}

}