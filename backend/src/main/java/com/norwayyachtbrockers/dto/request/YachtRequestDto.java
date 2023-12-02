package com.norwayyachtbrockers.dto.request;

import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.model.YachtImage;
import com.norwayyachtbrockers.model.YachtModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class YachtRequestDto {

    private Long id;

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

}
