package com.norwayyachtbrockers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "yacht_details")
@Getter
@Setter
public class YachtDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cabin")
    private Integer cabin;

    @Column(name = "berth")
    private Integer berth;

    @Column(name = "heads")
    private Integer heads;

    @Column(name = "shower")
    private Integer shower;

    @Column(name = "description", length = 5000)
    private String description;

    public YachtDetails() {
    }

    public YachtDetails(Integer cabin, Integer berth,
                        Integer heads, Integer shower, String description) {
        this.cabin = cabin;
        this.berth = berth;
        this.heads = heads;
        this.shower = shower;
        this.description = description;
    }
}
