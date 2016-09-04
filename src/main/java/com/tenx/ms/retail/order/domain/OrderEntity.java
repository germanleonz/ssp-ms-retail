package com.tenx.ms.retail.order.domain;


import com.tenx.ms.retail.store.domain.StoreEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="product_order")
public class OrderEntity {
    @Id
    @GeneratedValue
    @Column(name="order_id", nullable = false)
    private Long orderId;

    @Column(name="status", nullable = false, length = 7)
    private String status;

    @Column(name = "order_date", columnDefinition = "datetime default current_timestamp",
            insertable = false, nullable = false)
    private LocalDateTime orderDate;

    @Column(name="first_name", nullable = false, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = false)
    private List<OrderProductEntity> orderProducts;

    @ManyToOne(targetEntity = StoreEntity.class)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    public Long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductEntity> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderId=" + orderId +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", orderProducts=" + orderProducts +
                ", store=" + store +
                '}';
    }
}
