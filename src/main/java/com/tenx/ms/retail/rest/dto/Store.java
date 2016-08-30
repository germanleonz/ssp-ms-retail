package com.tenx.ms.retail.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("Store - Store Information holder")
public class Store {

    @ApiModelProperty(value = "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value="Store name")
    @NotNull
    private String name;

    public Long getStoreId() {
        return storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}