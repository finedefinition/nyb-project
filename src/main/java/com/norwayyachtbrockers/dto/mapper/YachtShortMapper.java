package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.YachtShortResponseDto;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.Yacht;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;

public class YachtShortMapper {
    public static YachtShortResponseDto convertToDto(Yacht yacht) {
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

    private static String formatPrice(BigDecimal price) {
        BigDecimal rounded = price.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return rounded.toPlainString();
    }
}
