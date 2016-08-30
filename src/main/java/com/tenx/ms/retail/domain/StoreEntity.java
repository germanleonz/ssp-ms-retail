package com.tenx.ms.retail.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue
    @Column(name="store_id")
    private Long storeId;

    @NotNull
    @Column(name="name")
    private String name;

    public StoreEntity() {
    }

    public StoreEntity(String name) {
        this.name = name;
    }

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
