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

/** OrderController calls the order processing service to apply promotions on the requested orders.
 * 
 * @author Vivek Malik
 *
 */

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired
	OrderProcessingService orderProcessingService;
	
	/** processOrder endpoint processes the  orderRequest and returns the OK response with the calculated order price 
	 * 
	 * @param orderRequest
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/process", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<OrderResponse> processOrder( @RequestBody OrderRequest orderRequest, BindingResult bindingResult) {
		log.info("Process Order request is {} ",orderRequest);
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<OrderResponse>(HttpStatus.BAD_REQUEST);
		}
		OrderResponse orderResponse = orderProcessingService.processOrder(orderRequest);
		return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK);
	}
	
}
