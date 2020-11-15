package com.org.promotionengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.model.OrderResponse;
import com.org.promotionengine.service.OrderProcessingService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired
	OrderProcessingService orderProcessingService;
	
	@PostMapping(value = "/process", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<OrderResponse> processOrder( @RequestBody OrderRequest orderRequest, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<OrderResponse>(HttpStatus.BAD_REQUEST);
		}
		OrderResponse orderResponse = orderProcessingService.processOrder(orderRequest);
		return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);
	}
	
}
