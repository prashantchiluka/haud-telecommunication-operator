package com.haud.telecom.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.haud.telecom.HaudTelecommunicationOperatorApplication;
import com.haud.telecom.service.SimCardService;
import com.haud.telecom.utils.Headers;

/**
 *
 * @author Prashanth
 *
 *         Test cases for Web API's of SimCard Module
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = HaudTelecommunicationOperatorApplication.class)
@ActiveProfiles({ "test" })
public class SimCardControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private SimCardService simCardService;

	private JSONObject json;

	@Before
	public void setUp() throws IOException {
		try {
			String jsonString = Files.readAllLines(Paths.get("src/test/resources/simcardcontroller-test-cases.json")).stream()
					.collect(Collectors.joining());

			json = new JSONObject(jsonString);
			this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		} catch (JSONException ex) {
			Logger.getLogger(CustomerControllerTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void createSimcardGiveCreatedWhenSimcardCreated() throws Exception {

		Mockito.when(simCardService.createSimCard(Mockito.any(), Mockito.anyString())).thenReturn(1l);

		mockMvc.perform(MockMvcRequestBuilders.post("/simcard/create").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("createSimcardValidJson")).header(Headers.USER_NAME, "matthew123"))
				.andExpect(status().isCreated());
	}

	@Test
	public void createSimcardGiveBadRequestWhenImsiIsNotPositive() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/simcard/create").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("createSimcardWithNonPositiveImsi")).header(Headers.USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors.[0].detail").value("invalid imsi"));
	}

	@Test
	public void createSimcardGiveBadRequestWhenMsisdnIsNotPositive() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/simcard/create").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("createSimcardWithNonPositiveMsisdn")).header(Headers.USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors.[0].detail").value("invalid msisdn"));
	}
}
