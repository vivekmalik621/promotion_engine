package com.org.promotionengine.service;

public interface PromotionAbstractFactory<T> {
	T getPromotionType(String promotionType) ;
}
