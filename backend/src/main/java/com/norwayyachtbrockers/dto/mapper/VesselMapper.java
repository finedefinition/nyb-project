package com.norwayyachtbrockers.dto.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norwayyachtbrockers.dto.request.VesselRequestDto;
import com.norwayyachtbrockers.dto.response.VesselImageDto;
import com.norwayyachtbrockers.dto.response.VesselResponseDto;
import com.norwayyachtbrockers.dto.response.VesselShortResponseDto;
import com.norwayyachtbrockers.model.Vessel;
import com.norwayyachtbrockers.util.ExchangeRateService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Component
public class VesselMapper {

    private final ExchangeRateService exchangeRateService;

    public VesselMapper(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public void updateVesselFromDto(Vessel existingVessel, VesselRequestDto vesselRequestDto) {
        existingVessel.setFeaturedVessel(vesselRequestDto.isFeaturedVessel());
        existingVessel.setVesselMake(vesselRequestDto.getVesselMake().trim());
        existingVessel.setVesselModel(vesselRequestDto.getVesselModel().trim());
        existingVessel.setVesselPrice(vesselRequestDto.getVesselPrice());
        existingVessel.setVesselYear(vesselRequestDto.getVesselYear());
        existingVessel.setVesselLocationCountry(vesselRequestDto.getVesselLocationCountry().trim());
        existingVessel.setVesselLocationState(vesselRequestDto.getVesselLocationState().trim());
        existingVessel.setVesselLengthOverall(vesselRequestDto.getVesselLengthOverall());
        existingVessel.setVesselBeam(vesselRequestDto.getVesselBeam());
        existingVessel.setVesselDraft(vesselRequestDto.getVesselDraft());
        existingVessel.setVesselCabin(vesselRequestDto.getVesselCabin());
        existingVessel.setVesselBerth(vesselRequestDto.getVesselBerth());
        existingVessel.setFuelType(vesselRequestDto.getFuelType());
        existingVessel.setKeelType(vesselRequestDto.getKeelType());
        existingVessel.setEngineQuantity(vesselRequestDto.getEngineQuantity());
        existingVessel.setVesselDescription(vesselRequestDto.getVesselDescription());
    }

    public VesselResponseDto toVesselResponseDto(Vessel vessel) {
        VesselResponseDto dto = new VesselResponseDto();
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
        dto.setVesselLengthOverall(vessel.getVesselLengthOverall());
        dto.setVesselBeam(vessel.getVesselBeam());
        dto.setVesselDraft(vessel.getVesselDraft());
        dto.setVesselCabin(vessel.getVesselCabin());
        dto.setVesselBerth(vessel.getVesselBerth());
        dto.setFuelType(vessel.getFuelType().toString());
        dto.setKeelType(vessel.getKeelType().toString());
        dto.setEngineQuantity(vessel.getEngineQuantity());
        dto.setVesselDescription(vessel.getVesselDescription());
        dto.setImageUrl(vessel.getImageKey());
        dto.setCreatedAt(vessel.getCreatedAt());
        List<VesselImageDto> vesselImages = Arrays.asList(
                new VesselImageDto(1L, "2a42821b-2bfb-4515-8fd0-2993af443bc4"),
                new VesselImageDto(2L, "9ca6d367-69ae-42c4-bcc9-09f5056c0742"),
                new VesselImageDto(3L, "966f25c3-87e4-41a3-bf19-87021f17e294"),
                new VesselImageDto(4L, "407e3456-30d4-4bfc-8bc6-22a309552401"),
                new VesselImageDto(5L, "83c535bb-2d57-4a9f-a3fc-babef98692b0"),
                new VesselImageDto(6L, "a141db09-d408-45c7-ae4e-02b7389f6009"),
                new VesselImageDto(7L, "d40f535a-7d08-4bbc-be32-c4439010dde6"),
                new VesselImageDto(8L, "4eb2ef50-db6c-4a74-8d32-e5a2249140b0")
        );

        dto.setVesselImages(vesselImages);
        return dto;
    }

    public VesselShortResponseDto toVesselShortResponseDto(VesselResponseDto vesselResponseDto) {
        VesselShortResponseDto shortDto = new VesselShortResponseDto();

        shortDto.setId(vesselResponseDto.getId());
        shortDto.setFeatured(vesselResponseDto.isFeatured());
        shortDto.setMake(vesselResponseDto.getMake());
        shortDto.setModel(vesselResponseDto.getModel());
        shortDto.setPrice(vesselResponseDto.getPrice());
        shortDto.setPriceUSD(vesselResponseDto.getPriceUSD());
        shortDto.setPriceGBP(vesselResponseDto.getPriceGBP());
        shortDto.setPriceNOK(vesselResponseDto.getPriceNOK());
        shortDto.setYear(vesselResponseDto.getYear());
        shortDto.setCountry(vesselResponseDto.getCountry());
        shortDto.setState(vesselResponseDto.getState());
        shortDto.setImageUrl(vesselResponseDto.getImageUrl());

        return shortDto;
    }

    private String formatPrice(BigDecimal price) {
        BigDecimal rounded = price.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return rounded.toPlainString();
    }
}
