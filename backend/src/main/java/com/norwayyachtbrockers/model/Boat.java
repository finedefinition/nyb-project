package com.norwayyachtbrockers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "boats")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String boatName;

    @Column(name = "price", nullable = false)
    private BigDecimal boatPrice;

    @Column(name = "brand", nullable = false)
    private String boatBrand;

    @Column(name = "year", nullable = false)
    private int boatYear;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Boat() {
    }

    public Boat(String boatName, BigDecimal boatPrice, String boatBrand, int boatYear, LocalDateTime createdAt) {
        this.boatName = boatName;
        this.boatPrice = boatPrice;
        this.boatBrand = boatBrand;
        this.boatYear = boatYear;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public BigDecimal getBoatPrice() {
        return boatPrice;
    }

    public void setBoatPrice(BigDecimal boatPrice) {
        this.boatPrice = boatPrice;
    }

    public String getBoatBrand() {
        return boatBrand;
    }

    public void setBoatBrand(String boatBrand) {
        this.boatBrand = boatBrand;
    }

    public int getBoatYear() {
        return boatYear;
    }

    public void setBoatYear(int boatYear) {
        this.boatYear = boatYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
