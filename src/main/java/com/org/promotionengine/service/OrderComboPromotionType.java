package com.org.promotionengine.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.promotionengine.data.Product;
import com.org.promotionengine.data.Promotion;
import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderPromotion;
import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.repo.PromotionRepository;

/**
 * OrderComboPromotionType processes Combo type promotion and calculates the order price
 * 
 * @author Vivek Malik
 *
 */
@Component
public class OrderComboPromotionType implements OrderPromotionType {

	@Autowired
	private PromotionRepository promotionRepository;

	@Override
	public BigDecimal process(final OrderPromotion orderPromotion) {

		final Map<String, Integer> appliedComboExcludedItemsMap = orderPromotion.getAppliedComboPromotionExcludedMap();
		final OrderItem orderItem = orderPromotion.getOrderItem();
		final String requestedSku = orderItem.getSku();
		final OrderRequest orderRequest = orderPromotion.getOrderRequest();
		BigDecimal orderPrice = orderPromotion.getOrderPrice();
		final Product product = orderPromotion.getProduct();

		final Promotion promotion = product.getPromotion();

		if (null == appliedComboExcludedItemsMap.get(requestedSku)) {

			Map<String, Integer> comboPromotionMap = new HashMap<String, Integer>();
			// create comboPromotionMap with the all SKU's applicable for the given Combo
			// Promotion
			promotionRepository.findByComboReferenceId(promotion.getComboReferenceId()).stream().forEach(comboPromo -> {
				comboPromotionMap.put(comboPromo.getProduct().getSku(), comboPromo.getQuantity());
			});

			if (OrderRequest.isComboValid(orderRequest, comboPromotionMap)) {
				orderPrice = orderPrice.add(promotion.getPrice());
				// if comboPromotion is valid on the requested item, then maintain the excluded
				// items map to capture other sku's for the Combo Promotion
				promotionRepository.findByComboReferenceId(promotion.getComboReferenceId()).stream()
						.forEach(comboPromo -> {
							appliedComboExcludedItemsMap.put(comboPromo.getProduct().getSku(),
									comboPromo.getQuantity());
						});
			} else {
				// if combo is not applicable, calculate unit price for the SKU's.
				orderPrice = orderPrice.add(product.getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity())));
			}
		}
		return orderPrice;
	}

}
