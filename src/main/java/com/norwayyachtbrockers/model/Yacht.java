package com.norwayyachtbrockers.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"yachtModel", "country", "town", "yachtImages", "yachtDetail", "ownerInfo", "favouritedByUsers"})
@Entity
@Table(name = "yachts")
@Data
public class Yacht extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "featured", nullable = false)
    private boolean featured;

    @Column(name = "vat_included", nullable = false)
    private boolean vatIncluded;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "price_old", precision = 10, scale = 2)
    private BigDecimal priceOld;

    @Column(name = "main_image_key", length = 40)
    private String mainImageKey;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "yacht_model_id")
    private YachtModel yachtModel;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "town_id")
    private Town town;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "yacht",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<YachtImage> yachtImages = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "yacht_detail_id", referencedColumnName = "id")
    private YachtDetail yachtDetail;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "owner_info_id", referencedColumnName = "id")
    private OwnerInfo ownerInfo;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "favouriteYachts")
    private Set<User> favouritedByUsers = new HashSet<>();

    public void setYachtModel(YachtModel yachtModel) {
        if (yachtModel != null && yachtModel.getYachts() == null) {
            yachtModel.setYachts(new HashSet<>());
        }
        this.yachtModel = yachtModel;
        if (this.yachtModel != null) {
            this.yachtModel.getYachts().add(this);
        }
    }

    public void setTown(Town town) {
        this.town = town;
        if (town != null && town.getYachts() == null) {
            town.setYachts(new HashSet<>());
        }
        if (town != null) {
            town.getYachts().add(this);
        }
    }

    public void setCountry(Country country) {
        this.country = country;
        if (this.country != null) {
            this.country.getYachts().remove(this);
        }

        // Add yacht to the new country
        if (country != null) {
            country.getYachts().add(this);
        }
    }
}

