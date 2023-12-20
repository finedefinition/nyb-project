package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"yacht_image_id"})
public class YachtImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("yacht_image_id")
    private Long id;

    @Column(name = "image_key")
    @JsonProperty("yacht_image_key")
    private String imageKey;

    @ManyToOne
    @JoinColumn(name = "yacht_id")
    @JsonIgnore
    private Yacht yacht;


    public YachtImage() {
    }

    public YachtImage(String imageKey) {
        this.imageKey = imageKey;
    }

    public void setYacht(Yacht yacht) {
        if (this.yacht != null) {
            this.yacht.getYachtImages().remove(this);
        }

        if (yacht != null) {
            yacht.getYachtImages().add(this);
        }

        this.yacht = yacht;
    }
}
