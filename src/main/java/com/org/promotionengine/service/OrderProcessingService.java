package com.org.promotionengine.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.model.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProcessingService {

	@Autowired
	PromotionService promotionService;
	
	public OrderResponse processOrder(final OrderRequest orderRequest) {
        BigDecimal orderPrice = promotionService.applyPromotion(orderRequest);
        //build response
        OrderResponse orderResponse = OrderResponse.builder().orderValue(orderPrice).build();
        return orderResponse;
    }
}
