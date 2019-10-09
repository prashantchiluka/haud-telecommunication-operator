package com.haud.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haud.api.impl.CustomerService;
import com.haud.dto.CustomerRequestDto;
import com.haud.dto.CustomerResponseDto;
import com.haud.dto.CustomerSimResponseDto;
import com.haud.dto.UpdateCustomerRequestDto;
import com.haud.entity.Customer;
import com.haud.mapper.CustomerMapper;
import com.haud.utils.Headers;
import com.haud.utils.Request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/customer")
@Api(tags = "Customer management Api", description = "API's of Customer management")
public class CustomerApi {

	@Autowired
	private CustomerService customerService;
    
    @PostMapping
    @ApiOperation(value = "Create customer", response = CustomerResponseDto.class, code = 201)
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestHeader(Headers.AUTH_USER_NAME) String userName,@RequestBody CustomerRequestDto request) {

    	Request.verifyCustomerPost(request);
    	Customer customer = CustomerMapper.toCustomer(request);
    	
    	customerService.addCustomer(customer,userName);
    	
        return new ResponseEntity<>(CustomerMapper.toCustomerResponse(customer), HttpStatus.CREATED);
    }
    
    @PutMapping("/{customerid}")
    @ApiOperation(value = "Link sim card to specific customer", response = Void.class, code = 204)
    public ResponseEntity<Void> linkSimCardToCustomer(@RequestHeader(Headers.AUTH_USER_NAME) String userName,@PathVariable("customerid") long customerId,
    		@RequestBody UpdateCustomerRequestDto request) {

    	List<Long> simCardId = request.getSimcardId();
    	Request.verifyCustomerPut(customerId, simCardId, customerService);
    	customerService.updateCustomer(customerId, simCardId,userName);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/{customerid}")
    @ApiOperation(value = "Get specific customer's sim cards", response = Void.class, code = 200)
    public ResponseEntity<CustomerSimResponseDto> getCustomerSim(@PathVariable ("customerid") long customerId) {

    	Request.verifyCustomerGet(customerId, customerService);
    	Customer customer = customerService.getCustomer(customerId);
    	
        return new ResponseEntity<>(CustomerMapper.toCustomerSimResponseDto(customer), HttpStatus.OK);
    }
}
