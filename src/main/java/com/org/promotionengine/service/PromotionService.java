package com.org.promotionengine.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PromotionService {

	 public BigDecimal applyPromotion(final OrderRequest orderRequest) {

	        BigDecimal orderPrice = BigDecimal.ZERO;

	        for (int i = 0; i < orderRequest.getItems().size(); i++) {
	            //apply promotions for received order Items
	            orderPrice = applyPromotionOnRequestedItems(orderRequest, orderPrice,
	                orderRequest.getItems().get(i));
	            log.info("Order Price: {} calculated ", orderPrice);
	        }
	        return orderPrice;
	    }
	
	 private BigDecimal applyPromotionOnRequestedItems(OrderRequest orderRequest,
		        BigDecimal orderPrice,OrderItem orderItem) {
					return orderPrice;
	 }

		
}
