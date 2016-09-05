package com.tenx.ms.retail.store.domain;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.product.domain.ProductEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue
    @Column(name="store_id", nullable = false)
    private Long storeId;

    @Column(name="name", nullable = false, length = 255)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
    private List<ProductEntity> products;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
    private List<OrderEntity> orders;

    public Long getStoreId() { return storeId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}