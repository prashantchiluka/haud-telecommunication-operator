package com.haud.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haud.entity.Customer;
import com.haud.entity.SimCard;
import com.haud.repository.CustomerRepository;
import com.haud.repository.SimCardRepository;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SimCardRepository simCardRepository;

	@Override
	public long addCustomer(Customer customer,String userName) {

		log.info("in service impl, adding customer " + customer);
		
		customer.setCreatedBy(userName);
		customer.setUpdatedBy(userName);

		log.info("in service impl, adding customer, repository call ");
		return customerRepository.save(customer).getId();

	}

	@Override
	public void updateCustomer(long customerId,List<Long> simId,String userName) {
		 
		log.info("in service impl, linking simcard to customer, sim card id: "+ simId + "customer id: "+ customerId);
		
		Customer customer = customerRepository.findById(customerId).get();
		List<SimCard> simCards = simCardRepository.findAllById(simId);
		
		customer.setSimCards(simCards);
		customer.setUpdatedBy(userName);
		
		customerRepository.save(customer);
		log.info("in service impl, linked simcard to customer");
	}

	@Override
	public Customer getCustomer(long customerId){
		
		log.info("in service impl, getting specific customer with id: "+customerId+ "repository call");
		
		Customer customer = customerRepository.findById(customerId).get();
		log.info("customer fetched from database, with customerid: " + customerId);
		return customer;
	}

	@Override
	public boolean isCustomerExist(long customerId) {
		
		log.info("in service impl isCustomerExist customerId: "+ customerId);
		return customerRepository.existsById(customerId);
		}
	
	
}
