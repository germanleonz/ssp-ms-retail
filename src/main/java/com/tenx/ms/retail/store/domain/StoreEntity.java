package com.tenx.ms.retail.store.domain;

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

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "store")
    private List<ProductEntity> products;

    public Long getStoreId() { return storeId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductEntity> getStoreProducts() {
        return products;
    }

    public void setStoreProducts(List<ProductEntity> products) {
        this.products = products;
    }
}