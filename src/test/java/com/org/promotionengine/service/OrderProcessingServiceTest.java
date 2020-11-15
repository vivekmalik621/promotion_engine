package com.org.promotionengine.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.model.OrderResponse;

@RunWith(MockitoJUnitRunner.class)
public class OrderProcessingServiceTest {

	 @InjectMocks
	    private OrderProcessingService orderProcessingService;

	    @Mock
	    private PromotionService promotionService;


	    @Test
	    public void testProcessOrder() {
	        Mockito.when(promotionService.applyPromotion(Mockito.any(OrderRequest.class))).thenReturn(new BigDecimal("300.00"));

	        List<OrderItem> orderItemList = new ArrayList<>();
	        OrderItem orderItem1 = new OrderItem("A",1);
	        OrderItem orderItem2 = new OrderItem("B",2);

	        orderItemList.add(orderItem1);
	        orderItemList.add(orderItem2);

	        OrderRequest orderRequest = OrderRequest.builder()
	            .items(orderItemList).build();

	        OrderResponse orderResponse = orderProcessingService.processOrder(orderRequest);
	        Assert.assertNotNull(orderResponse);
	        Assert.assertEquals(new BigDecimal("300.00"), orderResponse.getOrderValue());
	    }
}
