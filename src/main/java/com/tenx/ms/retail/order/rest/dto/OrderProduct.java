package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderProduct {
    @ApiModelProperty(name = "Order product id")
    private Long orderProductId;

    @ApiModelProperty(name = "Order Id")
    private Long orderId;

    @ApiModelProperty(name = "Product Id", required = true)
    @NotNull
    private Long productId;

    @ApiModelProperty(name = "Order Product count", required = true)
    @NotNull
    @Min(1)
    private Integer count;

    public Long getOrderProductId() {
        return orderProductId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}