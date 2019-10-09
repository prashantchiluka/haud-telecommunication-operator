package com.haud.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.haud.dto.CustomerResponseDto;
import com.haud.dto.SimRequestDto;
import com.haud.dto.SimResponseDto;
import com.haud.entity.Customer;
import com.haud.entity.SimCard;

public class SimMapper {

	public static SimCard toSimCard(SimRequestDto request) {

        return SimCard.builder()
        		.imsi(request.getImsi())
        		.msisdn(request.getMsisdn())
        		.build();
    }
	
	public static SimResponseDto toSimCardResponse(SimCard simCard) {

        return SimResponseDto.builder()
        		.id(simCard.getId())
        		.imsi(simCard.getImsi())
        		.msisdn(simCard.getMsisdn())
        		.build();
    }
	
	 public static List<SimResponseDto> toSimCardDto(List<SimCard> simCards) {

	      return simCards!=null ? simCards.stream().map(SimMapper::toSimCardResponse).collect(Collectors.toList()): new ArrayList<>();
	        }
}
