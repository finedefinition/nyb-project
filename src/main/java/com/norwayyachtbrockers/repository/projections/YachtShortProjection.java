package com.norwayyachtbrockers.repository.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class YachtShortProjection {

    private Long id;
    private boolean vatIncluded;
    private BigDecimal price;
    private BigDecimal priceOld;
    private String mainImageKey;
    private String make;
    private String model;
    private Integer year;
    private String country;
    private String town;
    private LocalDateTime createdAt;
    private Long favouritesCount;

    // Конструктор с параметрами, соответствующими выбранным полям
    public YachtShortProjection(Long id, boolean vatIncluded, BigDecimal price, BigDecimal priceOld, String mainImageKey,
                                String make, String model, Integer year, String country, String town,
                                LocalDateTime createdAt, Long favouritesCount) {
        this.id = id;
        this.vatIncluded = vatIncluded;
        this.price = price;
        this.priceOld = priceOld;
        this.mainImageKey = mainImageKey;
        this.make = make;
        this.model = model;
        this.year = year;
        this.country = country;
        this.town = town;
        this.createdAt = createdAt;
        this.favouritesCount = favouritesCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Long favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainImageKey() {
        return mainImageKey;
    }

    public void setMainImageKey(String mainImageKey) {
        this.mainImageKey = mainImageKey;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(BigDecimal priceOld) {
        this.priceOld = priceOld;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public boolean isVatIncluded() {
        return vatIncluded;
    }

    public void setVatIncluded(boolean vatIncluded) {
        this.vatIncluded = vatIncluded;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
