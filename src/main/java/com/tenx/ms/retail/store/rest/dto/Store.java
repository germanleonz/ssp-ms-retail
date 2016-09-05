package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@ApiModel("Store - Store Information holder")
public class Store {

    @ApiModelProperty(value = "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value="Store name")
    @NotBlank
    @Size(max = 255)
    private String name;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeId=" + storeId +
                ", name='" + name + '\'' +
                '}';
    }
}