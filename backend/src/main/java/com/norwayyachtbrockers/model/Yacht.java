package com.norwayyachtbrockers.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @Column(name = "vat_included", nullable = false)
    private boolean vatIncluded;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "main_image_key", length = 40)
    private String mainImageKey;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "yacht_model_id")
    private YachtModel yachtModel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "town_id")
    private Town town;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "yacht",
            cascade = CascadeType.ALL, orphanRemoval = true
    )
    private Set<YachtImage> yachtImages = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "yacht_detail_id", referencedColumnName = "id")
    private YachtDetail yachtDetail;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
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

    public void setYachtModel(YachtModel yachtModel) {
        this.yachtModel = yachtModel;
        yachtModel.getYachts().add(this);
    }

    // Convenience method to add a YachtImage to the Yacht
    public void addYachtImage(YachtImage yachtImage) {
        yachtImages.add(yachtImage);
        yachtImage.setYacht(this);
    }

    // Convenience method to remove a YachtImage from the Yacht
    public void removeYachtImage(YachtImage yachtImage) {
        yachtImages.remove(yachtImage);
        yachtImage.setYacht(null);
    }

    public void setTown(Town newTown) {
        // Remove yacht from the current town
        if (this.town != null) {
            this.town.getYachts().remove(this);
        }

        // Add yacht to the new town
        if (newTown != null) {
            newTown.getYachts().add(this);
        }

        this.town = newTown;
    }

    public void setCountry(Country newCountry) {
        // Remove yacht from the current country
        if (this.country != null) {
            this.country.getYachts().remove(this);
        }

        // Add yacht to the new country
        if (newCountry != null) {
            newCountry.getYachts().add(this);
        }

        this.country = newCountry;
    }

    public void setYachtDetail(YachtDetail yachtDetail) {
        this.yachtDetail = yachtDetail;
    }

    public void setOwnerInfo(OwnerInfo ownerInfo) {
        this.ownerInfo = ownerInfo;
    }
}

