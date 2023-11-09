package com.norwayyachtbrockers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "keel_type")
@Getter
@Setter
@ToString
public class Keel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id", nullable = false)
    private String name;

    public Keel(String name) {
        this.name = name;
    }

    public Keel() {
    }
}
