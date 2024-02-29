package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.response.VesselShortResponseDto;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.util.ExchangeRateService;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class VesselShortMapper {

    private final ExchangeRateService exchangeRateService;

    public VesselShortMapper(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public VesselShortResponseDto toVesselShortResponseDto(Vessel vessel) {
        VesselShortResponseDto dto = new VesselShortResponseDto();
        exchangeRateService.updateExchangeRates();
        BigDecimal gbpRate = exchangeRateService.getRate("GBP");
        BigDecimal usdRate = exchangeRateService.getRate("USD");
        BigDecimal nokRate = exchangeRateService.getRate("NOK");
        dto.setId(vessel.getId());
        dto.setFeatured(vessel.isFeaturedVessel());
        dto.setMake(vessel.getVesselMake());
        dto.setModel(vessel.getVesselModel());
        dto.setPrice(formatPrice(vessel.getVesselPrice()));
        dto.setPriceGBP(formatPrice(gbpRate.multiply(vessel.getVesselPrice())));
        dto.setPriceNOK(formatPrice(nokRate.multiply(vessel.getVesselPrice())));
        dto.setPriceUSD(formatPrice(usdRate.multiply(vessel.getVesselPrice())));
        dto.setYear(vessel.getVesselYear());
        dto.setCountry(vessel.getVesselLocationCountry());
        dto.setState(vessel.getVesselLocationState());
        dto.setImageUrl(vessel.getImageKey());
        return dto;
    }

    private String formatPrice(BigDecimal price) {
        BigDecimal rounded = price.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return rounded.toPlainString();
    }
}
