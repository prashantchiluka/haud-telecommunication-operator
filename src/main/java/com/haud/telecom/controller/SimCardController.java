package com.haud.telecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haud.telecom.dto.SimRequestDto;
import com.haud.telecom.dto.SimResponseDto;
import com.haud.telecom.entity.SimCard;
import com.haud.telecom.mapper.SimMapper;
import com.haud.telecom.service.SimCardService;
import com.haud.telecom.utils.Headers;
import com.haud.telecom.utils.Request;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/simcard")
@Slf4j
public class SimCardController {

	@Autowired
	private SimCardService simCardService;

	@PostMapping("/create")
	public ResponseEntity<SimResponseDto> createSimCard(@RequestHeader(Headers.USER_NAME) String userName,
			@RequestBody SimRequestDto request) {

		log.info("Saving sim card " + request);

		Request.verifySimCardPost(request);
		SimCard simCard = SimMapper.toSimCard(request);
		simCardService.createSimCard(simCard, userName);

		log.info("sim card saved with id " + simCard.getId());
		return new ResponseEntity<>(SimMapper.toSimCardResponse(simCard), HttpStatus.CREATED);
	}
}
