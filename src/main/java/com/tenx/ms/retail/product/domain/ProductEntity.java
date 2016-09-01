package com.tenx.ms.retail.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.math.BigDecimal;

@Entity
@Table(name="product")
public class ProductEntity {
    @Id
    @GeneratedValue
    @Column(name="product_id")
    private Long productId;

    @Column(name="store_id")
    private Long storeId;

    @Column(name="name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sku")
    private String sku;

    @Column(name = "price")
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
