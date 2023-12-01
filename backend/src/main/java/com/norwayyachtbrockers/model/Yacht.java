package com.norwayyachtbrockers.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "yachts")
@Getter
@Setter
public class Yacht {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "featured", nullable = false)
    private boolean featured;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "main_image_key", length = 40)
    private String mainImageKey;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "yacht_model_id")
    private YachtModel yachtModel;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "town_id")
    private Town town;

    @OneToMany(mappedBy = "yacht", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<YachtImage> yachtImages = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "yacht_detail_id", referencedColumnName = "id")
    private YachtDetail yachtDetail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_info_id", referencedColumnName = "id")
    private OwnerInfo ownerInfo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Yacht() {
    }

    public Yacht(boolean featured, BigDecimal price, String mainImageKey,
                 YachtModel yachtModel, Country country, Town town,
                 Set<YachtImage> yachtImages, YachtDetail yachtDetail, OwnerInfo ownerInfo, LocalDateTime createdAt) {
        this.featured = featured;
        this.price = price;
        this.mainImageKey = mainImageKey;
        this.yachtModel = yachtModel;
        this.country = country;
        this.town = town;
        this.yachtImages = yachtImages;
        this.yachtDetail = yachtDetail;
        this.ownerInfo = ownerInfo;
        this.createdAt = createdAt;
    }
}

