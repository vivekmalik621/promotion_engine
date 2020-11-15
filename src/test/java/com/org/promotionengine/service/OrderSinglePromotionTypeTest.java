package com.org.promotionengine.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.org.promotionengine.data.Product;
import com.org.promotionengine.data.Promotion;
import com.org.promotionengine.data.PromotionType;
import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderPromotion;
import com.org.promotionengine.model.OrderRequest;

@RunWith(MockitoJUnitRunner.class)
public class OrderSinglePromotionTypeTest {

	@InjectMocks
    OrderSinglePromotionType singlePromotionType;

    @Test
    public void testSinglePromotionsScenario1() {
    	  Product product = new Product();
          product.setId(1L);
          product.setSku("A");
          product.setUnitPrice(new BigDecimal(20));

          Promotion promotion = new Promotion();
          promotion.setProduct(product);
          promotion.setComboReferenceId(0L);
          promotion.setType(PromotionType.SINGLE.toString());
          promotion.setId(1L);
          promotion.setPrice(new BigDecimal(30));
          promotion.setQuantity(2);

          List<OrderItem> orderItemList = new ArrayList<>();
          OrderItem orderItem1 = new OrderItem("A", 2);
          OrderItem orderItem2 = new OrderItem("B", 2);

          orderItemList.add(orderItem1);
          orderItemList.add(orderItem2);

          OrderRequest orderRequest = OrderRequest.builder()
              .items(orderItemList).build();

          OrderPromotion orderPromotion = OrderPromotion.builder()
              .product(product).orderPrice(BigDecimal.ZERO).orderRequest(orderRequest)
              .orderItem(orderItem1).build();

          BigDecimal orderPrice = singlePromotionType.process(orderPromotion);
          Assert.assertNotNull(orderPrice);
          Assert.assertEquals(new BigDecimal("50"), orderPrice);
    }
    
    @Test
    public void testSinglePromotionsScenario2() {
        Product product = new Product();
        product.setId(1L);
        product.setSku("A");
        product.setUnitPrice(new BigDecimal(20));

        Promotion promotion = new Promotion();
        promotion.setProduct(product);
        promotion.setComboReferenceId(0L);
        promotion.setType(PromotionType.SINGLE.toString());
        promotion.setId(1L);
        promotion.setPrice(new BigDecimal(30));
        promotion.setQuantity(2);
        product.setPromotion(promotion);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem("A", 1);
        OrderItem orderItem2 = new OrderItem("B", 2);

        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        OrderRequest orderRequest = OrderRequest.builder()
            .items(orderItemList).build();

        OrderPromotion orderPromotion = OrderPromotion.builder()
            .product(product).orderPrice(BigDecimal.ZERO).orderRequest(orderRequest)
            .orderItem(orderItem1).build();

        BigDecimal orderPrice = singlePromotionType.process(orderPromotion);
        Assert.assertNotNull(orderPrice);
        Assert.assertEquals(new BigDecimal("20"), orderPrice);

    }

    @Test
    public void testSinglePromotionsScenario3() {
        Product product = new Product();
        product.setId(1L);
        product.setSku("A");
        product.setUnitPrice(new BigDecimal(20));

        Promotion promotion = new Promotion();
        promotion.setProduct(product);
        promotion.setComboReferenceId(0L);
        promotion.setType(PromotionType.SINGLE.toString());
        promotion.setId(1L);
        promotion.setPrice(new BigDecimal(30));
        promotion.setQuantity(2);
        product.setPromotion(promotion);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem("A", 1);
        OrderItem orderItem2 = new OrderItem("B", 1);

        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        OrderRequest orderRequest = OrderRequest.builder()
            .items(orderItemList).build();

        OrderPromotion orderPromotion = OrderPromotion.builder()
            .product(product).orderPrice(BigDecimal.ZERO).orderRequest(orderRequest)
            .orderItem(orderItem1).build();

        BigDecimal orderPrice = singlePromotionType.process(orderPromotion);
        Assert.assertNotNull(orderPrice);
        Assert.assertEquals(new BigDecimal("20"), orderPrice);

    }
	
}
