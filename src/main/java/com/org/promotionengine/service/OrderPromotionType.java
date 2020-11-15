package com.org.promotionengine.service;

import java.math.BigDecimal;

import com.org.promotionengine.model.OrderPromotion;

public interface OrderPromotionType {

	BigDecimal process(OrderPromotion orderPromotion);
	
}
