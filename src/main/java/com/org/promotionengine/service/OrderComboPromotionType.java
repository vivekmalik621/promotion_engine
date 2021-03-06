package com.org.promotionengine.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.promotionengine.data.Product;
import com.org.promotionengine.data.Promotion;
import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderPromotion;
import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.repo.ProductRepository;
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
	    
	    @Autowired
	    private ProductRepository productRepository;

	    @Override
	    public BigDecimal process(final OrderPromotion orderPromotion) {

	        final Map<String, Integer> appliedComboExcludedItemsMap = orderPromotion
	            .getAppliedComboPromotionExcludedMap();
	        final OrderItem orderItem = orderPromotion.getOrderItem();
	        final String requestedSku = orderItem.getSku();
	        final OrderRequest orderRequest = orderPromotion.getOrderRequest();
	        BigDecimal orderPrice = orderPromotion.getOrderPrice();
	        final Product product = orderPromotion.getProduct();

	        final Promotion promotion = product.getPromotion();
	        
			// check if requested order item has already been handled as part of earlier
			// combo order processing
			if (null == appliedComboExcludedItemsMap.get(requestedSku)) {

				Map<String, Integer> comboPromotionMap = new HashMap<>();
				// create comboPromotionMap with the all SKU's applicable for the given Combo
				// Promotion
				promotionRepository.findByComboReferenceId(promotion.getComboReferenceId())
	                .forEach(comboPromo -> comboPromotionMap.put(comboPromo.getProduct().getSku(), comboPromo.getQuantity()));

				Map<String, Integer> comboOrderItemsMap = new HashMap<>();
				// comboOrderItemsMap : contains all the requested skus matching the applicable Combo/offer
				orderRequest.getItems().forEach(orderRequestItem -> {
					if (comboPromotionMap.containsKey(orderRequestItem.getSku()))
						comboOrderItemsMap.put(orderRequestItem.getSku(), orderRequestItem.getQuantity());
				});

				Map<String, Integer> unitPriceComboItemsMap = new HashMap<>();
				Integer applicableCombos;

				if (comboOrderItemsMap.size() == comboPromotionMap.size()) {

					// calculate applicable combo sets for requested orders items
					applicableCombos = calculateApplicableCombos(comboPromotionMap, comboOrderItemsMap);

					if (applicableCombos > 0) {
						// update Order price for the count of applicable combo/offers
						orderPrice = orderPrice.add(promotion.getPrice().multiply(new BigDecimal(applicableCombos)));

						// identify unit orders/sku count for applicable combo items post applying applicable combos
	                    populateUnitPriceComboItemsMap(comboPromotionMap, comboOrderItemsMap,
	                        unitPriceComboItemsMap, applicableCombos);

	                    // if comboPromotion is valid on the requested item, then maintain the excluded
						// items map to capture other sku's for the Combo Promotion
						promotionRepository.findByComboReferenceId(promotion.getComboReferenceId())
								.forEach(comboPromo -> appliedComboExcludedItemsMap.put(comboPromo.getProduct().getSku(),
	                                    comboPromo.getQuantity()));
					} else {
						// if combo is not applicable, calculate using unit price for the SKU's.
						orderPrice = orderPrice
								.add(product.getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity())));
					}
					// add unit Price for extra items post applying combo on the combo order set
					for (Map.Entry<String, Integer> entryUnitPriceComboItem : unitPriceComboItemsMap.entrySet()) {
						if (entryUnitPriceComboItem.getValue() > 0)
							orderPrice = orderPrice
									.add(productRepository.findBySku(entryUnitPriceComboItem.getKey()).getUnitPrice())
									.multiply(new BigDecimal(entryUnitPriceComboItem.getValue()));
					}
				} else {
					// mismatch in requested order item and related combo promotion items, so combo
					// is not applicable, calculate unit price for the SKU's.
					orderPrice = orderPrice.add(product.getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity())));
				}
			}
	        return orderPrice;
	    }

		private void populateUnitPriceComboItemsMap(Map<String, Integer> comboPromotionMap,
				Map<String, Integer> comboOrderItemsMap, Map<String, Integer> unitPriceComboItemsMap,
				Integer applicableCombos) {

	        comboOrderItemsMap.forEach((key1, value1) -> comboPromotionMap.forEach((key, value) -> {
	            if (key1.equals(key)) {
	                Integer remainder = value1 - (applicableCombos * value);
	                unitPriceComboItemsMap.put(key1, remainder);
	            }
	        }));
	    }

		private Integer calculateApplicableCombos(Map<String, Integer> comboPromotionMap,
				Map<String, Integer> comboOrderItemsMap) {
			
			List<Integer> logicalApplicableComboCountList = new ArrayList<>();
	        comboOrderItemsMap.forEach((key1, value1) -> comboPromotionMap.forEach((key, value) -> {
	            if (key1.equals(key)) {
	                Integer quotient = value1 / value;
	                logicalApplicableComboCountList.add(quotient);
	            }
	        }));
			return Collections.min(logicalApplicableComboCountList);
		}
	}
