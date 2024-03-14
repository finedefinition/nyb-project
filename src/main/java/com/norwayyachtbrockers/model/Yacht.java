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
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "yachts")
@Getter
@Setter
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
            cascade = CascadeType.ALL, orphanRemoval = true
    )
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

    public Yacht() {
    }

    public Yacht(Long id, boolean featured, boolean vatIncluded, BigDecimal price,
                 String mainImageKey, YachtModel yachtModel, Country country,
                 Town town, Set<YachtImage> yachtImages, YachtDetail yachtDetail,
                 OwnerInfo ownerInfo, Set<User> favouritedByUsers) {
        this.id = id;
        this.featured = featured;
        this.vatIncluded = vatIncluded;
        this.price = price;
        this.mainImageKey = mainImageKey;
        this.yachtModel = yachtModel;
        this.country = country;
        this.town = town;
        this.yachtImages = yachtImages;
        this.yachtDetail = yachtDetail;
        this.ownerInfo = ownerInfo;
        this.favouritedByUsers = favouritedByUsers;
    }

    public void addFavouritedByUser(User user) {
        favouritedByUsers.add(user);
        user.getFavouriteYachts().add(this);
    }

    public void removeFavouritedByUser(User user) {
        favouritedByUsers.remove(user);
        user.getFavouriteYachts().remove(this); // Assuming User class has a getFavouriteYachts method.
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

}

