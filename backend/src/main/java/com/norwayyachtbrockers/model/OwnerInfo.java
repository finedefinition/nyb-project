package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "owner_infos", uniqueConstraints = { @UniqueConstraint(columnNames = {"email", "telephone" })})
@Getter
@Setter
@JsonPropertyOrder({"owner_info_id"})
public class OwnerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("owner_info_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "email", nullable = false)
    private String email;


    public OwnerInfo() {
    }

    public OwnerInfo(String firstName, String lastName, String telephone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.email = email;
    }
}
