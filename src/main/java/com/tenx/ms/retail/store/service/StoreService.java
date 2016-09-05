package com.tenx.ms.retail.store.service;

import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    private final ModelMapper mapper = new ModelMapper();

    public Optional<Store> getById(Long storeId) {
        Optional<StoreEntity> optionalStoreEntity = storeRepository.findOneByStoreId(storeId);
        return optionalStoreEntity.flatMap(entity -> Optional.of(mapper.map(entity, Store.class)));
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

    public void delete(Long storeId) throws ResourceNotFoundException {
        Optional<Store> optionalStore = this.getById(storeId);
        optionalStore.orElseThrow(() -> new ResourceNotFoundException(String.format("Store (%d) not found.", storeId)));
        storeRepository.delete(storeId);
    }
}