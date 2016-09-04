package com.tenx.ms.retail.order.rest.dto;

import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    @ApiModelProperty(value="Order id", readOnly = true)
    private Long orderId;

    @ApiModelProperty(value="Store id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Order date", readOnly = true)
    @DateTimeFormat
    private LocalDateTime orderDate;

    @ApiModelProperty(value = "Order status")
    @NotBlank
    private String status;

    @ApiModelProperty(value = "First name", required = true)
    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "\\A[a-zA-Z]+\\z", message = "First name must contain only letters")
    private String firstName;

    @ApiModelProperty(value = "Last name", required = true)
    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "\\A[a-zA-Z]+\\z", message = "Last name must contain only letters")
    private String lastName;

    @ApiModelProperty(value = "Email", required = true)
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value = "Phone number", required = true)
    @NotBlank
    @PhoneNumber
    private String phone;

    @ApiModelProperty(value = "Order Products", required = true)
    @NotNull
    @Size(min = 1)
    @Valid
    private List<OrderProduct> products;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", storeId=" + storeId +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", products=" + products +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
