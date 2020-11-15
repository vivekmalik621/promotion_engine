package com.org.promotionengine.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.model.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProcessingService {

	public OrderResponse processOrder(final OrderRequest orderRequest) {
        BigDecimal orderPrice = new BigDecimal(300);
        //build response
        OrderResponse orderResponse = OrderResponse.builder().orderValue(orderPrice).build();
        return orderResponse;
    }
}
