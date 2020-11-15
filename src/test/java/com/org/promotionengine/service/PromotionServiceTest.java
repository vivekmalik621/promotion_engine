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
import com.org.promotionengine.model.OrderItem;
import com.org.promotionengine.model.OrderRequest;

@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceTest {

	   @InjectMocks
	    private PromotionService promotionService;

	   @Test
	    public void testApplyWithoutPromotion() {

	    	Product product =new Product();  
	    	product.setId(1L);
	    	product.setSku("A");
	    	product.setUnitPrice(new BigDecimal(20));


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
}
