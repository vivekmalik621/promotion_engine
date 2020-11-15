package com.org.promotionengine.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.promotionengine.data.Product;
import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderPromotion;
import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.repo.ProductRepository;

import lombok.extern.slf4j.Slf4j;

/** PromotionService checks applicable promotion types and calculates the order price
 * 
 * @author Vivek Malik
 *
 */
@Service
@Slf4j
public class PromotionService {

	@Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionFactory<OrderPromotionType> promotionFactory;

    public BigDecimal applyPromotion(final OrderRequest orderRequest) {

        BigDecimal orderPrice = BigDecimal.ZERO;

        Map<String, Integer> appliedComboPromotionExcludedMap = new HashMap<>();

        for (int i = 0; i < orderRequest.getItems().size(); i++) {
            //apply promotions for received order Items
            orderPrice = applyPromotionOnRequestedItems(orderRequest, orderPrice,
                orderRequest.getItems().get(i),
                appliedComboPromotionExcludedMap);
            log.info("Order Price: {} calculated ", orderPrice);
        }
        return orderPrice;
    }

    private BigDecimal applyPromotionOnRequestedItems(OrderRequest orderRequest,
        BigDecimal orderPrice,
        OrderItem orderItem, Map<String, Integer> appliedComboExcludedItemsMap) {

        String requestedSku = orderItem.getSku();
        Optional<Product> product = Optional.ofNullable(productRepository.findBySku(requestedSku));

        if (product.isPresent() && null != product.get().getPromotion()) {
            //promotion applicable for the SKU
            OrderPromotion orderPromotion = OrderPromotion.builder().product(product.get())
                .orderPrice(orderPrice).orderRequest(orderRequest)
                .orderItem(orderItem).appliedComboPromotionExcludedMap(appliedComboExcludedItemsMap)
                .build();

            OrderPromotionType promotionType = promotionFactory
                .getPromotionType(product.get().getPromotion().getType());
            //process Promotions
            orderPrice = promotionType.process(orderPromotion);
        } else {
            //no promotion applicable for the SKU
            orderPrice = orderPrice
                .add(
                    product.get().getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        }
        return orderPrice;
    }

		
}
