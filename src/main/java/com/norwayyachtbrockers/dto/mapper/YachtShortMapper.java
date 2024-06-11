package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.YachtShortResponseDto;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.service.CountryService;
import com.norwayyachtbrockers.service.TownService;
import com.norwayyachtbrockers.service.YachtModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class YachtShortMapper {
    private final YachtModelService yachtModelService;
    private final TownService townService;
    private final CountryService countryService;

    public YachtShortResponseDto convertToDto(Yacht yacht) {
        YachtShortResponseDto dto = new YachtShortResponseDto();
        dto.setId(yacht.getId());
        dto.setVatIncluded(yacht.isVatIncluded());
        dto.setPrice(formatPrice(yacht.getPrice()));
        dto.setPriceOld(formatPrice(yacht.getPriceOld()));
        dto.setMainImageKey(yacht.getMainImageKey());
        // Yacht Model set
        dto.setMake(yacht.getYachtModel().getMake());
        dto.setModel(yacht.getYachtModel().getModel());
        dto.setYear(yacht.getYachtModel().getYear());
        // Country
        dto.setCountry(yacht.getCountry().getName());
        // Town
        dto.setTown(yacht.getTown().getName());

        //User set
        // Set yacht favourites as user IDs
        Set<Long> favouriteUserIds = yacht.getFavouritedByUsers().stream().map(User::getId).collect(Collectors.toSet());
        dto.setFavourites(favouriteUserIds);
        dto.setFavouritesCount((favouriteUserIds.size()));
        //Hot price
        if (yacht.getPriceOld().compareTo(yacht.getPrice()) > 0) {
            dto.setHotPrice(true);
        }
        // Created at
        dto.setCreatedAt(yacht.getCreatedAt());
        return dto;
    }

    private String formatPrice(BigDecimal price) {
        BigDecimal rounded = price.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return rounded.toPlainString();
    }
}
