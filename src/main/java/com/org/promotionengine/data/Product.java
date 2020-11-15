package com.org.promotionengine.data;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	private Long id;
	private String sku;
	private BigDecimal unitPrice;
	@OneToOne(mappedBy="product", cascade = CascadeType.ALL)
	private Promotion promotion;
	
}
