package com.org.promotionengine.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.org.promotionengine.data.Product;
import com.org.promotionengine.data.Promotion;
import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderPromotion;

@Component
public class OrderSinglePromotionType implements OrderPromotionType {

		  @Override
		    public BigDecimal process(OrderPromotion orderPromotion) {
		        Product product = orderPromotion.getProduct();
		        Promotion promotion = product.getPromotion();
		        OrderItem orderItem = orderPromotion.getOrderItem();
		        Integer requestedQuantity = orderItem.getQuantity();
		        BigDecimal orderPrice = orderPromotion.getOrderPrice();

		        if (requestedQuantity < promotion.getQuantity()) {
		            orderPrice = orderPrice
		                .add(product.getUnitPrice().multiply(new BigDecimal(requestedQuantity)));
		        } else if (requestedQuantity == promotion.getQuantity()) {
		            orderPrice = orderPrice.add(promotion.getPrice());
		        } else {
		            int quantityApplicableForUnitPrice = requestedQuantity % promotion.getQuantity();
		            int quantityApplicableForPromotions = requestedQuantity / promotion.getQuantity();
		            BigDecimal orderPriceWithoutPromotion = product.getUnitPrice()
		                .multiply(new BigDecimal(quantityApplicableForUnitPrice));
		            BigDecimal orderPriceWithPromotion = promotion.getPrice()
		                .multiply(new BigDecimal(quantityApplicableForPromotions));
		            orderPrice = orderPriceWithoutPromotion.add(orderPriceWithPromotion).add(orderPrice);
		        }
		        return orderPrice;
		    }

}
