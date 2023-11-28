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
import java.util.Set;

@Entity
@Table(name = "countries", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@JsonPropertyOrder({ "country_id", "country_name" })
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("country_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    @JsonProperty("country_name")
    private String name;

    @OneToMany(mappedBy = "country",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Town> towns;


    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }
}
