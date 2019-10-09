package com.haud.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haud.entity.Customer;
import com.haud.entity.SimCard;
import com.haud.repository.CustomerRepository;
import com.haud.repository.SimCardRepository;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SimCardRepository simCardRepository;

	@Override
	public long addCustomer(Customer customer,String userName) {

		customer.setCreatedBy(userName);
		customer.setUpdatedBy(userName);

		
		return customerRepository.save(customer).getId();

	}

	@Override
	public void updateCustomer(long customerId,List<Long> simId,String userName) {
		 
		Customer customer = customerRepository.findById(customerId).get();
		List<SimCard> simCards = simCardRepository.findAllById(simId);
		
		simCards.forEach(simCard-> {
			simCard.setCustomer(customer);
			simCard.setUpdatedBy(userName);
		});
		customer.setUpdatedBy(userName);
	}

	@Override
	public Customer getCustomer(long customerId){
		
		Customer customer = customerRepository.findById(customerId).get();
		return customer;
	}

	@Override
	public boolean isCustomerExist(long customerId) {
		return customerRepository.existsById(customerId);
		}
	
	
}
