package com.tenx.ms.retail.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
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

//    @ManyToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "order_id",
//            nullable = false, updatable = false, insertable = false)
//    private OrderEntity order;

    public Long getOrderProductId() {
        return orderProductId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

//    public OrderEntity getOrder() {
//        return order;
//    }

//    public void setOrder(OrderEntity order) {
//        this.order = order;
//    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderProductEntity{" +
                "orderProductId=" + orderProductId +
                ", count=" + count +
//                ", order=" + order +
                ", productId=" + productId +
                '}';
    }
}