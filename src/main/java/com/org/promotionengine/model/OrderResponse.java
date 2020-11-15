package com.org.promotionengine.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Value
@Builder
public class OrderResponse {

	private BigDecimal orderValue;
	
}
