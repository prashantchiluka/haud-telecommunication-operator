package com.haud.telecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haud.telecom.entity.SimCard;
import com.haud.telecom.repository.SimCardRepository;

import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@Slf4j
public class SimCardServiceImpl implements SimCardService{

	@Autowired
	private SimCardRepository simCardRepository;
	
	@Override
	public long createSimCard(SimCard simCard,String userName) {
		
		log.info("in service impl, adding simcard " + simCard);
		
		simCard.setCreatedBy(userName);
		simCard.setUpdatedBy(userName);
		
		log.info("in service impl, adding simcard, repository call ");
		return simCardRepository.save(simCard).getId();
	}
}
