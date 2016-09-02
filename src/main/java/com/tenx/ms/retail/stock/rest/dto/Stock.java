package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


public class Stock {
    @ApiModelProperty(value = "Store Id", readOnly = true)
    @NotNull
    private Long storeId;

    @ApiModelProperty(value = "Product Id", readOnly = true)
    @NotNull
    private Long productId;

    @ApiModelProperty(value = "Stock count")
    @NotNull
    private Integer count;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
