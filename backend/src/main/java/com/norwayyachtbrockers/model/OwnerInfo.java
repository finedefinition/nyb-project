//package com.norwayyachtbrockers.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "owner_info")
//@Getter
//@Setter
//public class OwnerInfo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//    @Column(name = "last_name", nullable = false)
//    private String lastName;
//
//    @Column(name = "telephone", nullable = false)
//    private String telephone;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @OneToOne(mappedBy = "ownerInfo",  cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Boat boat;
//
//    public OwnerInfo() {
//    }
//
//    public OwnerInfo(String firstName, String lastName, String telephone, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.telephone = telephone;
//        this.email = email;
//    }
//}
