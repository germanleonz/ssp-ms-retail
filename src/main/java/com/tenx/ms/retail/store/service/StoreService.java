package com.tenx.ms.retail.store.service;

import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    private final ModelMapper mapper = new ModelMapper();

    public Store getById(Long storeId) throws NoSuchElementException {
        Optional<StoreEntity> optionalStoreEntity = storeRepository.findOneByStoreId(storeId);
        return optionalStoreEntity.map(entity -> mapper.map(entity, Store.class)).get();
    }

    public List<Store> getAll() {
        List<StoreEntity> storeEntities = storeRepository.findAll();
        return storeEntities.stream().map(entity -> mapper.map(entity, Store.class)).collect(Collectors.toList());
    }

    public List<Store> getAllByName(String storeName) {
        List<StoreEntity> storeEntities = storeRepository.findAllByName(storeName);
        return storeEntities.stream().map(entity -> mapper.map(entity, Store.class)).collect(Collectors.toList());
    }

    @Transactional
    public Long create(Store store) {
        StoreEntity se = mapper.map(store, StoreEntity.class);
        se = storeRepository.save(se);
        return se.getStoreId();
    }

    public void delete(Long storeId) throws NoSuchElementException {
        Store store = this.getById(storeId);
        if (store != null) {
            storeRepository.delete(storeId);
        }
    }
}