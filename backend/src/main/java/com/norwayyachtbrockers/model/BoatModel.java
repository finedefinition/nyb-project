package com.norwayyachtbrockers.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "boat_models")
@Getter
@Setter
public class BoatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "make", nullable = false, length = 40)
    private String make;

    @Column(name = "model", nullable = false, length = 40)
    private String model;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "length_overall", nullable = false, precision = 5, scale = 2)
    private BigDecimal lengthOverall;

    @Column(name = "beam_width", nullable = false, precision = 5, scale = 2)
    private BigDecimal beamWidth;

    @Column(name = "draft_depth", nullable = false, precision = 5, scale = 2)
    private BigDecimal draftDepth;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "keel_type_id")
    private Keel keelType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "fuel_type_id")
    private Fuel fuelType;

    public BoatModel() {
    }

    public BoatModel(String make, String model, Integer year, BigDecimal lengthOverall,
                     BigDecimal beamWidth, BigDecimal draftDepth, Keel keelType, Fuel fuelType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.lengthOverall = lengthOverall;
        this.beamWidth = beamWidth;
        this.draftDepth = draftDepth;
        this.keelType = keelType;
        this.fuelType = fuelType;
    }
}