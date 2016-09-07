package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@ApiModel("Stock - stock information holder")
public class Stock {
    @ApiModelProperty(value = "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Product Id", readOnly = true)
    private Long productId;

    @ApiModelProperty(value = "Stock count")
    @NotNull
    @Min(1)
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
