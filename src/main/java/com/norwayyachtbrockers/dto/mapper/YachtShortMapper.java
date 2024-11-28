package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.YachtShortResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.projections.YachtShortProjection;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class YachtShortMapper {

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

        // User set
        // Set yacht favourites as user IDs
       dto.setFavouritesCount(yacht.getFavouritesCount());

        // Hot price (заполняем напрямую из базы данных)
        dto.setHotPrice(yacht.isFeatured()); // Прямое маппинг из сущности

        // Created at
        dto.setCreatedAt(yacht.getCreatedAt());

        return dto;
    }


    public YachtShortResponseDto convertProjectionToDto(YachtShortProjection projection) {
        YachtShortResponseDto dto = new YachtShortResponseDto();
        dto.setId(projection.getId());
        dto.setVatIncluded(projection.isVatIncluded());
        dto.setPrice(formatPrice(projection.getPrice()));
        dto.setPriceOld(formatPrice(projection.getPriceOld()));
        dto.setMainImageKey(projection.getMainImageKey());
        // Yacht Model set
        dto.setMake(projection.getMake());
        dto.setModel(projection.getModel());
        dto.setYear(projection.getYear());
        // Country
        dto.setCountry(projection.getCountry());
        // Town
        dto.setTown(projection.getTown());
        // Favourites count
        dto.setFavouritesCount(projection.getFavouritesCount().intValue());
        // Hot price
        if (projection.getPriceOld() != null && projection.getPriceOld().compareTo(projection.getPrice()) > 0) {
            dto.setHotPrice(true);
        }
        // Created at
        dto.setCreatedAt(projection.getCreatedAt());
        return dto;
    }

    private String formatPrice(BigDecimal price) {
        if (price == null) {
            return null;
        }
        BigDecimal rounded = price.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return rounded.toPlainString();
    }
}
