package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("location_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Location_Country"))
    private Country country;

    @ManyToOne
    @JoinColumn(name = "town_id", foreignKey = @ForeignKey(name = "FK_Location_Town"))
    private Town town;

    public Location() {
    }

    public Location(Country country, Town town) {
        this.country = country;
        this.town = town;
    }
}
