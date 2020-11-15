package com.org.promotionengine.data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Promotion {

	@Id
	private Long id;
	private Integer quantity;
	private BigDecimal price;
	private String type;
	private Long comboReferenceId;

	@OneToOne
	@JoinColumn(name ="sku_mapping")
	private Product product;
	
}
