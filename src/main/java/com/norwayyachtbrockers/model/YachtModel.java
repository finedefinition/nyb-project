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
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"keelType", "fuelType", "yachts"})
@Entity
@Table(name = "yacht_models",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"make", "model", "year"})})
@JsonPropertyOrder({ "yacht_model_id", "make", "model", "year",
        "lengthOverall", "beamWidth", "draftDepth", "fuelType", "keelType" })
@Data
public class YachtModel extends BaseEntity {

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
}