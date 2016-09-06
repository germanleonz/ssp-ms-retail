package com.tenx.ms.retail.order.service;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.order.exception.BackorderedItemsException;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.service.ProductService;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.service.StockService;
import com.tenx.ms.retail.store.domain.StoreEntity;
import org.springframework.stereotype.Service;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StockService stockService;

    private final ModelMapper mapper = new ModelMapper();

    @Transactional
    public Long create(Order order) throws NoSuchElementException, BackorderedItemsException {
        OrderEntity oe = mapper.map(order, OrderEntity.class);

        Long storeId = order.getStoreId();
        Optional<StoreEntity> se = storeRepository.findOneByStoreId(storeId);
        oe.setStore(se.orElseThrow(() -> new NoSuchElementException(String.format("Store (%d) not found", storeId))));

        for (OrderProductEntity orderProduct: oe.getOrderProducts()) {
            Long productId = orderProduct.getProductId();
            Product product = productService.getById(storeId, productId);
            if (product == null) {
                throw new NoSuchElementException(String.format("Product (%d) not found", productId));
            }

            Stock stock = stockService.findById(storeId, productId);
            if (stock.getCount() >= orderProduct.getCount()) {
                Stock stockUpdate = new Stock();
                stockUpdate.setStoreId(storeId);
                stockUpdate.setProductId(productId);
                stockUpdate.setCount(stock.getCount() - orderProduct.getCount());
                stockService.upsert(stockUpdate);
            } else {
                throw new BackorderedItemsException();
            }
        }

        oe = orderRepository.save(oe);

        return oe.getOrderId();
    }
}
