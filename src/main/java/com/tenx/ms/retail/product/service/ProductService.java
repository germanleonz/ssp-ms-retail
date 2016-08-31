package com.tenx.ms.retail.product.service;

import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    private final ModelMapper mapper = new ModelMapper();

    public Optional<Product> getById(Long storeId, Long productId) {
        Optional<ProductEntity> optionalProductEntity = repo.findOneByStoreIdAndProductId(storeId, productId);
        return optionalProductEntity.flatMap(entity -> Optional.of(mapper.map(entity, Product.class)));
    }

    public List<Product> getAll(Long storeId) {
        List<ProductEntity> productEntities = repo.findAllByStoreId(storeId);
        return productEntities.stream().map(entity -> mapper.map(entity, Product.class)).collect(Collectors.toList());
    }

    public List<Product> getAllByName(Long storeId, String productName) {
        List<ProductEntity> productEntities = repo.findAllByStoreIdAndName(storeId, productName);
        return productEntities.stream().map(entity -> mapper.map(entity, Product.class)).collect(Collectors.toList());
    }

    @Transactional
    public Long create(Product product) {
        ProductEntity pe = mapper.map(product, ProductEntity.class);
        pe = repo.save(pe);
        return pe.getProductId();
    }

}
