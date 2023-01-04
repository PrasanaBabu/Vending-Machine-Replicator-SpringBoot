package com.ford.springbootstart.Entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer pId;
    @Column(name = "product_cost")
    private Double pCost;
    @Column(name = "product_quantity")
    private Integer quantity;
    @Column(name = "product_name")
    private String name;

    public Product() {
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Double getpCost() {
        return pCost;
    }

    public void setpCost(Double pCost) {
        this.pCost = pCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product(Integer pId, Double pCost, Integer quantity, String name) {
        this.pId = pId;
        this.pCost = pCost;
        this.quantity = quantity;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pId=" + pId +
                ", pCost=" + pCost +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}
