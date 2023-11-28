package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "towns", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "country_id"})})
@JsonPropertyOrder({ "town_id", "name" })
@Getter
@Setter
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("town_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    @JsonProperty("town_name")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "country_id", nullable = false)
    @JsonProperty("country_name")
    private Country country;

    public Town() {
    }

    public Town(String name, Country country) {
        this.name = name;
        this.country = country;
    }
}