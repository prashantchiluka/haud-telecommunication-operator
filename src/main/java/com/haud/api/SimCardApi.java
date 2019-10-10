package com.haud.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haud.api.impl.SimCardService;
import com.haud.dto.SimRequestDto;
import com.haud.dto.SimResponseDto;
import com.haud.entity.SimCard;
import com.haud.mapper.SimMapper;
import com.haud.utils.Headers;
import com.haud.utils.Request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/simcard")
@Api(tags = "Sim card management api", description = "API's of Sim card management")
@Slf4j
public class SimCardApi {
	
	@Autowired
	private SimCardService simCardService;

	@PostMapping
    @ApiOperation(value = "Create sim card", response = SimResponseDto.class, code = 201)
	public ResponseEntity<SimResponseDto> createSimCard(@RequestHeader(Headers.AUTH_USER_NAME) String userName,@RequestBody SimRequestDto request) {

		log.info("Creating sim card " + request);
		
		Request.verifySimCardPost(request);
		SimCard simCard = SimMapper.toSimCard(request);
		simCardService.createSimCard(simCard,userName);
		
		log.info("sim card created with id "+ simCard.getId());
        return new ResponseEntity<>(SimMapper.toSimCardResponse(simCard), HttpStatus.CREATED);
	}
}
