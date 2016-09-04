package com.tenx.ms.retail.order.repository;

import com.tenx.ms.retail.order.domain.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long>{
}
