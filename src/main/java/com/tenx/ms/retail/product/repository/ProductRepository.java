package com.tenx.ms.retail.product.repository;

import com.tenx.ms.retail.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("PMD.MethodNamingConventions")
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    List<ProductEntity> findAllByStore_StoreId(Long storeId);

    List<ProductEntity> findAllByStore_StoreIdAndName(Long storeId, String productName);

    Optional<ProductEntity> findOneByStore_StoreIdAndProductId(Long storeId, Long productId);
}
