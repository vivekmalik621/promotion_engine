package com.org.promotionengine.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.model.OrderResponse;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	  @InjectMocks
	    private OrderController orderController;
	  
	  @Test
	    public void testProcessOrderBadRequestScenario() {

	        OrderResponse response = OrderResponse.builder().orderValue(new BigDecimal("300.00")).build();

	        List<OrderItem> orderItemList = new ArrayList<>();

	        OrderRequest orderRequest = OrderRequest.builder()
	            .items(orderItemList).build();

	        BindingResult bindingResult = Mockito.mock(BindingResult.class);
	        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

	        ResponseEntity<OrderResponse> mockedResponse = orderController.processOrder(orderRequest,bindingResult);
	        Assert.assertNotNull(mockedResponse);
	        Assert.assertEquals(HttpStatus.BAD_REQUEST, mockedResponse.getStatusCode());

	    }
	
}
