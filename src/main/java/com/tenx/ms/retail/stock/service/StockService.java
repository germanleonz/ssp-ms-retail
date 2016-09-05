package com.tenx.ms.retail.stock.service;

import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Transactional
    public void upsert(Stock stock) {
        Long storeId = stock.getStoreId();
        Long productId = stock.getProductId();
        StockEntity se = mapper.map(stock, StockEntity.class);

        Optional<StoreEntity> ste = storeRepository.findOneByStoreId(storeId);
        Optional<ProductEntity> pe = productRepository.findOneByStore_StoreIdAndProductId(storeId, productId);

        se.setStore(ste.orElseThrow(() -> new ResourceNotFoundException("Stock's store not found")));
        se.setProduct(pe.orElseThrow(() -> new ResourceNotFoundException("Stock's product not found")));

        stockRepository.save(se);
    }

    public Optional<Stock> findById(Long storeId, Long productId) {
        Optional<StockEntity> optionalStockEntity = stockRepository.findOneByStore_StoreIdAndProductId(storeId, productId);
        return optionalStockEntity.flatMap(entity -> Optional.of(mapper.map(entity, Stock.class)));
    }
}
