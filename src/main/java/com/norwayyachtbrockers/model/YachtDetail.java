package com.norwayyachtbrockers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "yacht_details")
@JsonPropertyOrder({"yacht_detail_id"})
@Data
public class YachtDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("yacht_detail_id")
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
}
