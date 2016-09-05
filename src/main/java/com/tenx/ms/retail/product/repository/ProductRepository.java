package com.tenx.ms.retail.product.repository;

import com.tenx.ms.retail.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    List<ProductEntity> findAllByStoreStoreId(Long storeId);

    List<ProductEntity> findAllByStoreStoreIdAndName(Long storeId, String productName);

    Optional<ProductEntity> findOneByStoreStoreIdAndProductId(Long storeId, Long productId);
}
