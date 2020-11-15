package com.org.promotionengine.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.promotionengine.data.Product;
import com.org.promotionengine.data.Promotion;
import com.org.promotionengine.data.PromotionType;
import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderPromotion;
import com.org.promotionengine.model.OrderRequest;
import com.org.promotionengine.repo.PromotionRepository;

@RunWith(MockitoJUnitRunner.class)
public class OrderComboPromotionTypeTest {

	 @InjectMocks
	    private OrderComboPromotionType comboPromotionType;

	    @Mock
	    private PromotionRepository promotionRepository;

	    @Test
	    public void processComboPromotionType() {

	        List<Promotion> promotionList = new ArrayList<>();

	        Product product1 = new Product();
	        product1.setId(1L);
	        product1.setSku("A");
	        product1.setUnitPrice(new BigDecimal(20));

	        Promotion promotion1 = new Promotion();
	        promotion1.setComboReferenceId(1L);
	        promotion1.setType(PromotionType.COMBO.toString());
	        promotion1.setId(1L);
	        promotion1.setPrice(new BigDecimal(30));
	        promotion1.setQuantity(2);
	        promotion1.setProduct(product1);
	        product1.setPromotion(promotion1);

	        promotionList.add(promotion1);

	        Product product2 = new Product();
	        product2.setId(2L);
	        product2.setSku("B");
	        product2.setUnitPrice(new BigDecimal(30));

	        Promotion promotion2 = new Promotion();
	        promotion2.setProduct(product2);
	        promotion2.setComboReferenceId(1L);
	        promotion2.setType(PromotionType.COMBO.toString());
	        promotion2.setId(2L);
	        promotion2.setPrice(new BigDecimal(30));
	        promotion2.setQuantity(2);
	        product2.setPromotion(promotion2);

	        promotionList.add(promotion2);

	        Mockito.when(promotionRepository.findByComboReferenceId(Mockito.anyLong()))
	            .thenReturn(promotionList);

	        List<OrderItem> orderItemList = new ArrayList<>();
	        OrderItem orderItem1 = new OrderItem("A", 2);
	        OrderItem orderItem2 = new OrderItem("B", 2);

	        orderItemList.add(orderItem1);
	        orderItemList.add(orderItem2);

	        OrderRequest orderRequest = OrderRequest.builder()
	            .items(orderItemList).build();

	        Map<String, Integer> appliedComboPromotionExcludedMap = new HashMap<>();

	        OrderPromotion orderPromotion = OrderPromotion.builder()
	            .product(product1).orderPrice(BigDecimal.ZERO).orderRequest(orderRequest)
	            .appliedComboPromotionExcludedMap(appliedComboPromotionExcludedMap)
	            .orderItem(orderItem1).build();

	        BigDecimal orderPrice = comboPromotionType.process(orderPromotion);
	        Assert.assertNotNull(orderPrice);
	        Assert.assertEquals(new BigDecimal("30"), orderPrice);
	    }

}
