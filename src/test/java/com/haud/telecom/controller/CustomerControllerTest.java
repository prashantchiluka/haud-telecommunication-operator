package com.haud.telecom.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
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
import com.haud.telecom.entity.Customer;
import com.haud.telecom.entity.SimCard;
import com.haud.telecom.service.CustomerService;
import com.haud.telecom.utils.Headers;

/**
*
* @author Prashanth
*
* Test cases for Web API's of Customer Module
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = HaudTelecommunicationOperatorApplication.class)
@ActiveProfiles({"test"})
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
	private CustomerService customerService;
    
    private JSONObject json;

    @Before
    public void setUp() throws IOException {
        try {
            String jsonString = Files.readAllLines(Paths.get("src/test/resources/customercontroller-test-cases.json")).stream()
                    .collect(Collectors.joining());

            json = new JSONObject(jsonString);
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        } catch (JSONException ex) {
            Logger.getLogger(CustomerControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
    @Test
    public void getCustomerSimGiveBadRequestWhenIdIsLessThenOrEqualToZero() throws Exception {
    	
    	 mockMvc.perform(MockMvcRequestBuilders.get("/customer/0"))
         .andExpect(status().isBadRequest())
         .andExpect(jsonPath("$.errors.[0].detail").value("id must be greater than zero"));
    }
    
    @Test
    public void getCustomerSimGiveBadRequestWhenCustomerNotExist() throws Exception {
    	
         Mockito.when(customerService.isCustomerExist(Mockito.anyLong())).thenReturn(false);

    	 mockMvc.perform(MockMvcRequestBuilders.get("/customer/100"))
         .andExpect(status().isBadRequest())
         .andExpect(jsonPath("$.errors.[0].detail").value("customer not exist"));
    }
    
    @Test
    public void getCustomerSimGiveCustomersSim() throws Exception {
    	
    	SimCard simCard1 = SimCard.builder().id(1l).imsi(1234l).msisdn(5676l).build();
    	SimCard simCard2 = SimCard.builder().id(2l).imsi(1234l).msisdn(5676l).build();

    	List<SimCard> simCards = Arrays.asList(simCard1,simCard2);
    	Customer customer = Customer.builder()
    			.id(1l)
    			.name("Prashanth")
    			.email("abc@gmail.com")
    			.simCards(simCards)
    			.build();
    	
         Mockito.when(customerService.isCustomerExist(Mockito.anyLong())).thenReturn(true);
         Mockito.when(customerService.getCustomer(Mockito.anyLong())).thenReturn(customer);
         
    	 mockMvc.perform(MockMvcRequestBuilders.get("/customer/1"))
         .andExpect(status().isOk())	
         .andExpect(jsonPath("$.customrId").value(1))
         .andExpect(jsonPath("$.simCardId.[0]").value(1))
         .andExpect(jsonPath("$.simCardId.[1]").value(2));
    }
    
    @Test
    public void saveCustomerGiveBadRequestWhenCustomerNameIsNull() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(json.getString("saveCustomerWithNullName")).header(Headers.AUTH_USER_NAME, "matthew123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].detail").value("customer name cannot be null or empty"));
    }
    
    @Test
    public void saveCustomerGiveBadRequestWhenCustomerNameIsEmpty() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(json.getString("saveCustomerWithEmptyName")).header(Headers.AUTH_USER_NAME, "matthew123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].detail").value("customer name cannot be null or empty"));
    }
    
    @Test
    public void saveCustomerGiveBadRequestWhenCustomerEmailIsNull() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(json.getString("saveCustomerWithNullEmail")).header(Headers.AUTH_USER_NAME, "matthew123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].detail").value("customer email cannot be null or empty"));
    }
    
    @Test
    public void saveCustomerGiveBadRequestWhenCustomerEmailIsEmpty() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(json.getString("saveCustomerWithEmptyEmail")).header(Headers.AUTH_USER_NAME, "matthew123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].detail").value("customer email cannot be null or empty"));
    }
    
    @Test
    public void saveCustomerGiveBadRequestWhenCustomerEmailIsInvalid() throws Exception {
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(json.getString("saveCustomerWithInvalidEmail")).header(Headers.AUTH_USER_NAME, "matthew123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].detail").value("invalid email pattern"));
    }
    
    @Test
    public void saveCustomerGiveCreatedWhenCustomerIsCreated() throws Exception {
    	
        Mockito.when(customerService.addCustomer(Mockito.any(),Mockito.anyString())).thenReturn(1l);

    	mockMvc.perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON)
                .content(json.getString("saveCustomerValidJson")).header(Headers.AUTH_USER_NAME, "matthew123"))
                .andExpect(status().isCreated());
    }
    
    @Test
	public void linkSimCardToCustomerGiveBadRequestWhenSimListIsEmpty() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.put("/customer/1").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerEmptyList")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.[0].detail").value("sim card id expected"));
	}
	
    @Test
	public void linkSimCardToCustomerGiveBadRequestWhenNullSimIdFound() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.put("/customer/1").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerNullElementInList")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.[0].detail").value("invalid List of Id"));
	}
	
    @Test
	public void linkSimCardToCustomerGiveBadRequestWhenNegativeSimIdFound() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.put("/customer/1").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerNegativeIdtInList")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.[0].detail").value("invalid List of Id"));
	}
	
    @Test
	public void linkSimCardToCustomerGiveBadRequestWhenZeroSimIdFound() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.put("/customer/1").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerZeroIdInList")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.[0].detail").value("invalid List of Id"));
	}
    
    @Test
	public void linkSimCardToCustomerGiveBadRequestWhenZeroCustomerIdPassed() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.put("/customer/0").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerValidJson")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.[0].detail").value("id must be greater than zero"));
	}
	
    @Test
	public void linkSimCardToCustomerGiveBadRequestWhenNegativeCustomerIdPassed() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.put("/customer/-1").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerValidJson")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.[0].detail").value("id must be greater than zero"));
	}
	
    @Test
	public void linkSimCardToCustomerGiveBadRequestWhenCustomerNotPresent() throws Exception {

        Mockito.when(customerService.isCustomerExist(Mockito.anyLong())).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/customer/100").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerValidJson")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.[0].detail").value("customer not exist"));
	}
	
    @Test
	public void linkSimCardToCustomerGiveNoContentWhenLinkingDoneSuccessfully() throws Exception {

        Mockito.when(customerService.isCustomerExist(Mockito.anyLong())).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/customer/100").contentType(MediaType.APPLICATION_JSON)
				.content(json.getString("linkSimCardToCustomerValidJson")).header(Headers.AUTH_USER_NAME, "matthew123"))
				.andExpect(status().isNoContent());
	}
	
}
