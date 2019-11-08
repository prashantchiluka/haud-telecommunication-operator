package com.haud.telecom.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haud.telecom.entity.Customer;
import com.haud.telecom.entity.SimCard;
import com.haud.telecom.repository.CustomerRepository;
import com.haud.telecom.repository.SimCardRepository;
import com.haud.telecom.service.CustomerServiceImpl;

/**
 *
 * @author Prashanth
 *
 *         Test cases for methods of CustomerService.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private SimCardRepository simCardRepository;
	@InjectMocks
	private CustomerServiceImpl customerService;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addCustomerReturnAddedCustomerId() {

		Customer customer = Customer.builder().id(1l).build();
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);

		assertEquals(1l, customerService.addCustomer(new Customer(), "Matthew fa"));
	}

	@Test
	public void getCustomerReturnCustomer() {

		Customer customer = Customer.builder().id(1l).email("abc@gmail.com").build();

		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));

		assertEquals(1l, customerService.getCustomer(1l).getId());
		assertEquals("abc@gmail.com", customerService.getCustomer(1l).getEmail());
	}

	@Test
	public void isCustomerExistReturnTrueIfCustomerExist() {

		Mockito.when(customerRepository.existsById(Mockito.anyLong())).thenReturn(true);
		assertEquals(true, customerService.isCustomerExist(1l));

	}

	@Test
	public void isCustomerExistReturnFalseIfCustomerNotExist() {

		Mockito.when(customerRepository.existsById(Mockito.anyLong())).thenReturn(false);
		assertEquals(false, customerService.isCustomerExist(100l));
	}

	@Test
	public void updateCustomerLinkSimcardWithCustomer() {

		Customer customer = Customer.builder().id(1l).email("abc@gmail.com").build();

		SimCard simCard1 = SimCard.builder().id(1l).imsi(1234l).msisdn(5676l).build();
		SimCard simCard2 = SimCard.builder().id(2l).imsi(1234l).msisdn(5676l).build();

		List<SimCard> simCards = Arrays.asList(simCard1, simCard2);

		Mockito.when(customerRepository.findById(Mockito.any())).thenReturn(Optional.of(customer));

		Mockito.when(simCardRepository.findAllById(Mockito.anyList())).thenReturn(simCards);
		customerService.updateCustomer(1l, Arrays.asList(1l, 2l), "Matthew fa");
	}

}
