package com.example.splitwise_expences;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SplitwiseExpencesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void userCanBeCreatedReadUpdatedAndDeleted() throws Exception {
		mockMvc.perform(get("/api/users"))
				.andExpect(status().isUnauthorized());

		MvcResult createResult = mockMvc.perform(post("/api/users")
						.with(httpBasic("test-user", "test-password"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"name":"  Alex Example  ","email":"Alex@Example.com"}
								"""))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(jsonPath("$.name").value("Alex Example"))
				.andExpect(jsonPath("$.email").value("alex@example.com"))
				.andReturn();
		String location = createResult.getResponse().getHeader("Location");

		mockMvc.perform(get(location).with(httpBasic("test-user", "test-password")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("alex@example.com"));

		mockMvc.perform(get("/api/users").with(httpBasic("test-user", "test-password")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Alex Example"));

		mockMvc.perform(put(location)
						.with(httpBasic("test-user", "test-password"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"name":"Alex Updated","email":"alex.updated@example.com"}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Alex Updated"));

		mockMvc.perform(post("/api/users")
						.with(httpBasic("test-user", "test-password"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"name":"Another User","email":"ALEX.UPDATED@example.com"}
								"""))
				.andExpect(status().isConflict())
				.andExpect(content().string(containsString("Email already registered")));

		mockMvc.perform(post("/api/users")
						.with(httpBasic("test-user", "test-password"))
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"name":"","email":"not-an-email"}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fieldErrors.name").exists())
				.andExpect(jsonPath("$.fieldErrors.email").exists());
		mockMvc.perform(delete(location)
						.with(httpBasic("test-user", "test-password")))
				.andExpect(status().isNoContent());

		mockMvc.perform(get(location).with(httpBasic("test-user", "test-password")))
				.andExpect(status().isNotFound());
	}
}
