package com.haud.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haud.entity.SimCard;
import com.haud.repository.SimCardRepository;
@Service
@Transactional
public class SimCardServiceImpl implements SimCardService{

	@Autowired
	private SimCardRepository simCardRepository;
	
	@Override
	public long createSimCard(SimCard simCard,String userName) {
		
		simCard.setCreatedBy(userName);
		simCard.setUpdatedBy(userName);
		
		return simCardRepository.save(simCard).getId();
	}
}
