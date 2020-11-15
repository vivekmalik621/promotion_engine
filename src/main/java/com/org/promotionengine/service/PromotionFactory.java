package com.org.promotionengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.promotionengine.data.PromotionType;

@Component
public class PromotionFactory <T> implements PromotionAbstractFactory<OrderPromotionType> {

    @Autowired
    private OrderSinglePromotionType singlePromotionType;

    @Autowired
    private OrderComboPromotionType comboPromotionType;

    @Override
    public OrderPromotionType getPromotionType(String promotionType) {
        if (PromotionType.SINGLE.toString().equals(promotionType)) {
            return singlePromotionType;
        } else if(PromotionType.COMBO.toString().equals(promotionType)) {
           return comboPromotionType;
        }
        throw new IllegalArgumentException("Invalid Promotion Type") ;
    }
	
}
