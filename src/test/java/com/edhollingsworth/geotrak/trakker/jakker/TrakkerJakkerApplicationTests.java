package com.edhollingsworth.geotrak.trakker.jakker;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class TrakkerJakkerApplicationTests {
	@Autowired
    MockMvc mockMvc;
    
	@LocalServerPort
    int randomServerPort;
	
	/*@Test
    void testQuery() throws Exception {
		String expectedResponse = "{test:test}";
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())//.andExpect(content().json(expectedResponse))
                .andReturn();
    }*/
		
	@Test
	void contextLoads() {
	}

}
