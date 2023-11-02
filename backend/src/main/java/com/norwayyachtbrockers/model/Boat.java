package com.norwayyachtbrockers.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "boats")
@Getter
@Setter
@ToString
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Column(name = "image_key")
    private String imageKey;

    @Column(name = "place")
    private String boatPlace;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_info_id", nullable = true)
    private OwnerInfo ownerInfo;

    public Boat() {
    }

    public Boat(String boatName, BigDecimal boatPrice, String boatBrand, int boatYear,
                LocalDateTime createdAt, String imageKey, String boatPlace, OwnerInfo ownerInfo) {
        this.boatName = boatName;
        this.boatPrice = boatPrice;
        this.boatBrand = boatBrand;
        this.boatYear = boatYear;
        this.createdAt = createdAt;
        this.imageKey = imageKey;
        this.boatPlace = boatPlace;
        this.ownerInfo = ownerInfo;
    }

}
