package com.org.promotionengine.model;

import java.math.BigDecimal;
import java.util.Map;

import com.org.promotionengine.data.Product;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
@ToString
public class OrderPromotion {

	private BigDecimal orderPrice;
	private OrderItem orderItem;
	private Product product;
	private OrderRequest orderRequest;
	Map<String, Integer> appliedComboPromotionExcludedMap;
}
