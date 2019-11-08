package com.haud.telecom.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.haud.telecom.entity.SimCard;
import com.haud.telecom.repository.SimCardRepository;
import com.haud.telecom.service.SimCardServiceImpl;

/**
 *
 * @author Prashanth
 *
 *         Test cases for methods of SimCard service.
 */
public class SimCardServiceTest {

	@Mock
	private SimCardRepository simCardRepository;
	@InjectMocks
	private SimCardServiceImpl simCardService;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createSimCardReturnCreatedSimId() {

		SimCard simCard = SimCard.builder().id(1l).imsi(1234l).msisdn(5676l).build();
		Mockito.when(simCardRepository.save(Mockito.any())).thenReturn(simCard);

		assertEquals(1l, simCardService.createSimCard(new SimCard(), "Jack Ma"));
	}
}
