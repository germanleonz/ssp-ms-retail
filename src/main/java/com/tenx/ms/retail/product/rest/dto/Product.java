package com.tenx.ms.retail.product.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@ApiModel("Product - Product Information holder")
public class Product {
    @ApiModelProperty(value = "Product Id", readOnly = true)
    private Long productId;

    @ApiModelProperty(value = "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Product name")
    @NotBlank
    @Size(max = 255)
    private String name;

    @ApiModelProperty(value = "Product description")
    @NotBlank
    @Size(max = 255)
    private String description;

    @ApiModelProperty(value = "Product SKU - Stock Keeping Unit")
    @NotBlank
    @Size(min=5, max=10)
    private String sku;

    @ApiModelProperty(value = "Product price")
    @NotNull
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) { this.productId = productId; }

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
