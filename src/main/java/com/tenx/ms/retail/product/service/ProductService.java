package com.tenx.ms.retail.product.service;

import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    private final ModelMapper mapper = new ModelMapper();

    public Optional<Product> getById(Long storeId, Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRepository.findOneByStore_StoreIdAndProductId(storeId, productId);
        return optionalProductEntity.flatMap(entity -> Optional.of(mapper.map(entity, Product.class)));
    }

    public List<Product> getAll(Long storeId) {
        List<ProductEntity> productEntities = productRepository.findAllByStore_StoreId(storeId);
        return productEntities.stream().map(entity -> mapper.map(entity, Product.class)).collect(Collectors.toList());
    }

    public List<Product> getAllByName(Long storeId, String productName) {
        List<ProductEntity> productEntities = productRepository.findAllByStore_StoreIdAndName(storeId, productName);
        return productEntities.stream().map(entity -> mapper.map(entity, Product.class)).collect(Collectors.toList());
    }

    @Transactional
    public Long create(Product product) throws ResourceNotFoundException {
        ProductEntity pe = mapper.map(product, ProductEntity.class);

        Optional<StoreEntity> se = storeRepository.findOneByStoreId(product.getStoreId());

        pe.setStore(se.orElseThrow(() -> new ResourceNotFoundException("Product's store not found")));

        pe = productRepository.save(pe);
        return pe.getProductId();
    }
}
