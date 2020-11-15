package com.org.promotionengine.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.promotionengine.data.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findBySku(String sku);
}
