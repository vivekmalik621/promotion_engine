package com.org.promotionengine.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@lombok.Value
@Builder
@EqualsAndHashCode
@ToString
@Valid
public class OrderRequest {

   @NotEmpty
   @NotNull
   @Valid
	public List<OrderItem> items;
	
   @JsonCreator
	public OrderRequest(
		@JsonProperty("items") List<OrderItem> items) {
		this.items = items;
	}
}
