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
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "yacht_images")
@JsonPropertyOrder({"yacht_image_id"})
@Data
public class YachtImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("yacht_image_id")
    private Long id;

    @Column(name = "image_key")
    @JsonProperty("yacht_image_key")
    private String imageKey;

    @Column(name = "image_index")
    @JsonProperty("yacht_image_index")
    private Integer imageIndex;

    @ManyToOne
    @JoinColumn(name = "yacht_id")
    @JsonIgnore
    private Yacht yacht;

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
