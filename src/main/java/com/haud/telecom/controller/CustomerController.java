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

	@PostMapping("/save")
	public ResponseEntity<CustomerResponseDto> saveCustomer(@RequestHeader(Headers.USER_NAME) String userName,
			@RequestBody CustomerRequestDto request) {

		log.info("Saving customer " + request);

		Request.verifyCustomerPost(request);
		Customer customer = CustomerMapper.toCustomer(request);

		customerService.addCustomer(customer, userName);

		log.info("Customer saved in database with id " + customer.getId());

		return new ResponseEntity<>(CustomerMapper.toCustomerResponse(customer), HttpStatus.CREATED);
	}

	@PutMapping("/save/{customerid}")
	public ResponseEntity<Void> bindSimCardToCustomer(@RequestHeader(Headers.USER_NAME) String userName,
			@PathVariable("customerid") long customerId, @RequestBody UpdateCustomerRequestDto request) {

		List<Long> simCardId = request.getSimcardId();

		log.info("Binding sim card to customer , sim card id: " + simCardId + "customer id: " + customerId);

		Request.verifyCustomerPut(customerId, simCardId, customerService);
		customerService.updateCustomer(customerId, simCardId, userName);

		log.info("Customer id :" + customerId + "linked with sim card id :" + simCardId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{customerid}")
	public ResponseEntity<CustomerSimResponseDto> getSpecificCustomerSim(@PathVariable("customerid") long customerId) {

		log.info("Getting Specific customer's sim with customer id :" + customerId);

		Request.verifyCustomerGet(customerId, customerService);
		Customer customer = customerService.getCustomer(customerId);

		log.info("Customer retrived with id " + customerId);
		return new ResponseEntity<>(CustomerMapper.toCustomerSimResponseDto(customer), HttpStatus.OK);
	}
}
