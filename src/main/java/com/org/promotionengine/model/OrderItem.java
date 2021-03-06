package com.org.promotionengine.model;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
@ToString
public class OrderItem {

	@NotNull
	@NotEmpty
	private String sku;

	@NotNull
	@NotEmpty
	private Integer quantity;
	
	@JsonCreator
	public OrderItem(
		@JsonProperty("sku") String sku,
		@JsonProperty("quantity") Integer quantity) {
		this.sku = sku;
		this.quantity = quantity;
	}
	
}
