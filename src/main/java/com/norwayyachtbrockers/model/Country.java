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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@JsonPropertyOrder({ "country_id", "country_name" })
@Getter
@Setter
public class Country extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("country_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    @JsonProperty("country_name")
    private String name;

    @OneToMany(mappedBy = "country",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Town> towns;

    @OneToMany(mappedBy = "country",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Yacht> yachts = new HashSet<>();

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public void addYacht(Yacht yacht) {
        yachts.add(yacht);
        yacht.setCountry(this);
    }

    public void removeYacht(Yacht yacht) {
        yachts.remove(yacht);
        if (yacht.getCountry() == this) {
            yacht.setCountry(null);
        }
    }

    public Set<Yacht> getYachts() {
        return yachts;
    }

    public void setYachts(Set<Yacht> yachts) {
        this.yachts = yachts;
    }

    public void addTown(Town town) {
        towns.add(town);
        town.setCountry(this);
    }

    public void removeTown(Town town) {
        towns.remove(town);
        if (town.getCountry() == this) {
            town.setCountry(null);
        }
    }
}
