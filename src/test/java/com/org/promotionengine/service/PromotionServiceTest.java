package com.org.promotionengine.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.org.promotionengine.repo.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceTest {

    @InjectMocks
    private PromotionService promotionService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PromotionFactory promotionFactory;

    @Test
    public void testApplyWithoutPromotion() {

    	Product product =new Product();  
    	product.setId(1L);
    	product.setSku("A");
    	product.setUnitPrice(new BigDecimal(20));

        Mockito.when(productRepository.findBySku(Mockito.anyString())).thenReturn(product);

    	List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem("A",2);
        OrderItem orderItem2 = new OrderItem("B",2);

        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        OrderRequest orderRequest = OrderRequest.builder()
            .items(orderItemList).build();
        
        BigDecimal orderPrice= promotionService.applyPromotion(orderRequest);
        Assert.assertNotNull(orderPrice);
        Assert.assertEquals(new BigDecimal("80"), orderPrice);

    }

    @Test
    public void testApplyWithSingleTypePromotion() {

        Product product =new Product();
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

        Mockito.when(productRepository.findBySku(Mockito.anyString())).thenReturn(product);
        OrderSinglePromotionType orderPromotionType =  Mockito.mock(OrderSinglePromotionType.class);
        Mockito.when(promotionFactory.getPromotionType(Mockito.anyString())).thenReturn(orderPromotionType);
        Mockito.when(orderPromotionType.process(Mockito.any(OrderPromotion.class))).thenReturn(new BigDecimal(50));

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem("A",2);
        OrderItem orderItem2 = new OrderItem("B",2);

        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        OrderRequest orderRequest = OrderRequest.builder()
            .items(orderItemList).build();

        BigDecimal orderPrice= promotionService.applyPromotion(orderRequest);
        Assert.assertNotNull(orderPrice);
        Assert.assertEquals(new BigDecimal("50"), orderPrice);

    }
	   
	   
}
