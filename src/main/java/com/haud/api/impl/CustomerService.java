package com.haud.api.impl;

import java.util.List;

import com.haud.entity.Customer;

public interface CustomerService {

	long addCustomer(Customer customer,String userName);
	
	void updateCustomer(long customerId, List<Long> simId,String userName);

	Customer getCustomer(long customerId);
	
	boolean isCustomerExist(long customerId);
}
