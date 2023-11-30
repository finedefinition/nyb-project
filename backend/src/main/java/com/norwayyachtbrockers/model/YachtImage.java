package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "yacht_images")
@Getter
@Setter
public class YachtImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("yacht_image_id")
    private Long id;

    @Column(name = "image_key", nullable = false)
    private String imageKey;

    @ManyToOne
    @JoinColumn(name = "yacht_id")
    private Yacht yacht;


    public YachtImage() {
    }

    public YachtImage(String imageKey) {
        this.imageKey = imageKey;
    }
}
