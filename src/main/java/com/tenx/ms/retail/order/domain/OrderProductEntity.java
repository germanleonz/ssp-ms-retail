package com.tenx.ms.retail.order.domain;

import com.tenx.ms.retail.product.domain.ProductEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_product")
public class OrderProductEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_product_id", nullable = false, insertable = false)
    private Long orderProductId;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "product_id", nullable = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product;

    public Long getOrderProductId() {
        return orderProductId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}