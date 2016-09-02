package com.tenx.ms.retail.order.service;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.domain.OrderProductEntity;
import com.tenx.ms.retail.order.repository.OrderProductRepository;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.store.repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Transactional
    public Long create(Order order) throws ResourceNotFoundException {
        OrderEntity oe = mapper.map(order, OrderEntity.class);

        Long storeId = order.getStoreId();
        Optional<StoreEntity> se = storeRepository.findOneByStoreId(storeId);
        oe.setOrderStore(se.orElseThrow(() -> new ResourceNotFoundException("Order store not found")));

        LOGGER.info("ObjectEntity {}", oe);

        for (OrderProductEntity product: oe.getOrderProducts()) {
            Long productId = product.getProduct().getProductId();
            Optional<ProductEntity> pe = productRepository.findOneByStore_StoreIdAndProductId(storeId, productId);
            product.setProduct(pe.orElseThrow(() -> new ResourceNotFoundException("Order Product not found")));
        }

        LOGGER.info("Order before save {}", oe);

        oe = orderRepository.save(oe);

        LOGGER.info("Order after save {}", oe);

        for (OrderProductEntity product: oe.getOrderProducts()) {
            product.setOrder(oe);
        }
        orderProductRepository.save(oe.getOrderProducts());

        return oe.getOrderId();
    }
}
