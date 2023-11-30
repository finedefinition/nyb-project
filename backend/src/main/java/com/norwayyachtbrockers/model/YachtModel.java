package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "yacht_models",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"make", "model", "year"})})
@JsonPropertyOrder({ "yacht_model_id", "make", "model", "year",
        "lengthOverall", "beamWidth", "draftDepth", "fuelType", "keelType" })
public class YachtModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("yacht_model_id")
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

    @OneToMany(mappedBy = "yachtModel",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Yacht> yachts = new HashSet<>();

    public YachtModel() {
    }

    public YachtModel(String make, String model, Integer year, BigDecimal lengthOverall,
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getLengthOverall() {
        return lengthOverall;
    }

    public void setLengthOverall(BigDecimal lengthOverall) {
        this.lengthOverall = lengthOverall;
    }

    public BigDecimal getBeamWidth() {
        return beamWidth;
    }

    public void setBeamWidth(BigDecimal beamWidth) {
        this.beamWidth = beamWidth;
    }

    public BigDecimal getDraftDepth() {
        return draftDepth;
    }

    public void setDraftDepth(BigDecimal draftDepth) {
        this.draftDepth = draftDepth;
    }

    public Keel getKeelType() {
        return keelType;
    }

    public void setKeelType(Keel keelType) {
        this.keelType = keelType;
    }

    public Fuel getFuelType() {
        return fuelType;
    }

    public void setFuelType(Fuel fuelType) {
        this.fuelType = fuelType;
    }

}