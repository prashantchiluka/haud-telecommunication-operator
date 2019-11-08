package com.haud.telecom.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.haud.telecom.dto.CustomerRequestDto;
import com.haud.telecom.dto.SimRequestDto;
import com.haud.telecom.exception.ClientException;
import com.haud.telecom.service.CustomerService;
import com.haud.telecom.utils.Errors.Error;

/**
 * @author Prashanth
 *
 *         This is utility class for validating request body and other
 *         parameters.
 *
 */
public class Request {

	private Request(){}
	
	private static final String INVALID_POST_REQUEST = "invalid POST request";
	private static final String INVALID_PUT_REQUEST = "invalid PUT request";
	private static final String INVALID_GET_REQUEST = "invalid GET request";

	
	public static void verifyCustomerPost(CustomerRequestDto request) {

		Errors errors = new Errors();

		if (StringUtils.isEmpty(request.getName())) {
			errors.addError(Error.builder().title(INVALID_POST_REQUEST).detail("customer name cannot be null or empty")
					.build());
		}

		if (StringUtils.isEmpty(request.getEmail())) {
			errors.addError(Error.builder().title(INVALID_POST_REQUEST).detail("customer email cannot be null or empty")
					.build());
		}

		if (!errors.getErrors().isEmpty()) {
			throw new ClientException(HttpStatus.BAD_REQUEST, errors);
		}
		
		if(isValidEmailPattern(request.getEmail())){
			
			errors.addError(Error.builder().title(INVALID_POST_REQUEST).detail("invalid email pattern")
					.build());
			throw new ClientException(HttpStatus.BAD_REQUEST, errors);
		}
	}
	
	public static void verifyCustomerPut(long customerId,List<Long> simCardId,CustomerService customerService){
		
		verifyId(customerId,INVALID_PUT_REQUEST);
		
		verifyIdList(simCardId, "sim card id expected", INVALID_PUT_REQUEST);
		
		verifyIdExist(customerId, customerService,INVALID_PUT_REQUEST);
	}
	
	public static void verifyCustomerGet(long customerId,CustomerService customerService){
		
		verifyId(customerId,INVALID_GET_REQUEST);
		verifyIdExist(customerId, customerService,INVALID_GET_REQUEST);

	}
	
	public static void verifySimCardPost(SimRequestDto request){
		
		Errors errors = new Errors();
		
		if(!isValidNumber(request.getImsi())){
			
			errors.addError(Error.builder().title(INVALID_POST_REQUEST).detail("invalid imsi")
					.build());
		}
		
		if(!isValidNumber(request.getMsisdn())){
			
			errors.addError(Error.builder().title("INVALID_POST_REQUEST").detail("invalid msisdn")
					.build());
		}

		if (!errors.getErrors().isEmpty()) {
			throw new ClientException(HttpStatus.BAD_REQUEST, errors);
		}
	}
	
	private static void verifyIdList(List<Long> id, String message, String title) {

		Errors errors = new Errors();

		if (id == null || id.isEmpty()) {
			errors.addError(

					Error.builder().title(title).detail(message).build());
			throw new ClientException(HttpStatus.BAD_REQUEST, errors);
		}

		if (id.stream().anyMatch(i -> i == null || i <= 0)) {

			errors.addError(

					Error.builder().title(title).detail("invalid List of Id").build());
			throw new ClientException(HttpStatus.BAD_REQUEST, errors);
		}
	}
	
	private static void verifyId(long id , String title) {

		Errors errors = new Errors();
		if (id <= 0) {
			errors.addError(Error.builder().title(title).detail("id must be greater than zero")
					.build());

			throw new ClientException(HttpStatus.BAD_REQUEST, errors);
		}
	}
	
	private static boolean isValidNumber(long number) {

		return number > 0;
	}
	
	private static void verifyIdExist(long customerId,CustomerService customerService,String title){
		
		Errors errors = new Errors();

		if(!customerService.isCustomerExist(customerId)){
			errors.addError(Error.builder().title(title).detail("customer not exist")
					.build());
			
			throw new ClientException(HttpStatus.BAD_REQUEST, errors);
		}
	}
	
	private static boolean isValidEmailPattern(String email){
		
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return !matcher.matches();
		
	}
}
