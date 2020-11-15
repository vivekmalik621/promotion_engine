package com.org.promotionengine.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.promotionengine.data.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

	List<Promotion> findByComboReferenceId(Long comboReferenceId);
}
