package com.tenx.ms.retail.stock.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "stock")
public class StockEntity {
    @Id
    @Column(name = "product_id", insertable = false, updatable = false, nullable = false)
    private Long productId;

    @Column(name = "count", nullable = false)
    private Integer count;

    @OneToOne(targetEntity = ProductEntity.class)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;

    @ManyToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    private StoreEntity store;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProductEntity getProduct() { return product; }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}