package com.algofi.dataviz.app;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppApplicationTests {
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void testBitcoin() throws Exception {
		mvc.perform(get("/bitcoin")
			    .param("dataset_code", "WIKI")
				.param("database_code", "AAPL")
				.param("api_key", "DKczFdjuL_16KZVxeZKk")
				.param("transformation","rdiff")
				.param("collapse", "quarterly")
				.param("order", "asc")
				.param("end_date", "2018-05-01")
				.param("start_date", "2018-05-01")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is(200)))
		        .andExpect(jsonPath("$.context.headers.Content-Type[0].type", is("application")))
		        .andExpect(jsonPath("$.context.headers.Content-Type[0].subtype", is("json")));
	}

}
