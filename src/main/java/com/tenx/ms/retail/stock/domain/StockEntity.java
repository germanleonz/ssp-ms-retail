package com.tenx.ms.retail.stock.domain;

//import com.tenx.ms.retail.product.domain.ProductEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumns;
//import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "stock")
public class StockEntity {
    @EmbeddedId
    private StockId stockId;

    @Column(name = "count", nullable = false)
    private Integer count;

//    @OneToOne(targetEntity = ProductEntity.class)
//    @JoinColumns(value = {
//            @JoinColumn(name = "store_id", referencedColumnName = "store_id"),
//            @JoinColumn(name = "product_id", referencedColumnName = "product_id")
//    })
//    @MapsId("store_id")
//    private ProductEntity product;

    public StockId getStockId() {
        return stockId;
    }

    public void setStockId(StockId stockId) {
        this.stockId = stockId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

//    public ProductEntity getProduct() { return product; }
//
//    public void setProduct(ProductEntity product) {
//        this.product = product;
//    }
}