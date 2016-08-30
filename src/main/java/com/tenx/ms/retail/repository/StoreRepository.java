package com.tenx.ms.retail.repository;

import com.tenx.ms.retail.domain.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    List<StoreEntity> findAllByName(String storeName);
}