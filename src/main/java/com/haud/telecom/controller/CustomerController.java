package com.haud.telecom.controller;

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

import com.haud.telecom.dto.CustomerRequestDto;
import com.haud.telecom.dto.CustomerResponseDto;
import com.haud.telecom.dto.CustomerSimResponseDto;
import com.haud.telecom.dto.UpdateCustomerRequestDto;
import com.haud.telecom.entity.Customer;
import com.haud.telecom.mapper.CustomerMapper;
import com.haud.telecom.service.CustomerService;
import com.haud.telecom.utils.Headers;
import com.haud.telecom.utils.Request;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<CustomerResponseDto> createCustomer(@RequestHeader(Headers.AUTH_USER_NAME) String userName,
			@RequestBody CustomerRequestDto request) {

		log.info("Creating customer " + request);

		Request.verifyCustomerPost(request);
		Customer customer = CustomerMapper.toCustomer(request);

		customerService.addCustomer(customer, userName);

		log.info("Customer created in database with id " + customer.getId());

		return new ResponseEntity<>(CustomerMapper.toCustomerResponse(customer), HttpStatus.CREATED);
	}

	@PutMapping("/{customerid}")
	public ResponseEntity<Void> linkSimCardToCustomer(@RequestHeader(Headers.AUTH_USER_NAME) String userName,
			@PathVariable("customerid") long customerId, @RequestBody UpdateCustomerRequestDto request) {

		List<Long> simCardId = request.getSimcardId();

		log.info("Linking sim card to customer , sim card id: " + simCardId + "customer id: " + customerId);

		Request.verifyCustomerPut(customerId, simCardId, customerService);
		customerService.updateCustomer(customerId, simCardId, userName);

		log.info("Customer id :" + customerId + "linked with sim card id :" + simCardId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{customerid}")
	public ResponseEntity<CustomerSimResponseDto> getCustomerSim(@PathVariable("customerid") long customerId) {

		log.info("Getting customer's sim with customer id :" + customerId);

		Request.verifyCustomerGet(customerId, customerService);
		Customer customer = customerService.getCustomer(customerId);

		log.info("Customer retrived with id " + customerId);
		return new ResponseEntity<>(CustomerMapper.toCustomerSimResponseDto(customer), HttpStatus.OK);
	}
}
