package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.service.CountryService;
import com.norwayyachtbrockers.service.OwnerInfoService;
import com.norwayyachtbrockers.service.TownService;
import com.norwayyachtbrockers.service.YachtDetailService;
import com.norwayyachtbrockers.service.YachtModelService;
import com.norwayyachtbrockers.util.ExchangeRateService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class YachtMapper {
    private final YachtModelService yachtModelService;

    private final TownService townService;

    private final CountryService countryService;

    private final YachtDetailService yachtDetailService;

    private final OwnerInfoService ownerInfoService;

    private final ExchangeRateService exchangeRateService;

    public YachtMapper(YachtModelService yachtModelService, TownService townService,
                       CountryService countryService, YachtDetailService yachtDetailService,
                       OwnerInfoService ownerInfoService, ExchangeRateService exchangeRateService) {
        this.yachtModelService = yachtModelService;
        this.townService = townService;
        this.countryService = countryService;
        this.yachtDetailService = yachtDetailService;
        this.ownerInfoService = ownerInfoService;
        this.exchangeRateService = exchangeRateService;
    }

    public void updateYachtFromDto(Yacht yacht, YachtRequestDto dto) {
        updateFields(yacht, dto);
    }

    public Yacht convertToYacht(YachtRequestDto dto) {
        Yacht yacht = new Yacht();
        updateFields(yacht, dto);
        return yacht;
    }

    public YachtResponseDto convertToDto(Yacht yacht) {
        YachtResponseDto dto = new YachtResponseDto();
        exchangeRateService.updateExchangeRates();
        BigDecimal gbpRate = exchangeRateService.getRate("GBP");
        BigDecimal usdRate = exchangeRateService.getRate("USD");
        BigDecimal nokRate = exchangeRateService.getRate("NOK");

        dto.setId(yacht.getId());
        dto.setFeatured(yacht.isFeatured());
        dto.setVatIncluded(yacht.isVatIncluded());
        dto.setPrice(formatPrice(yacht.getPrice()));
        dto.setPriceGBP(formatPrice(gbpRate.multiply(yacht.getPrice())));
        dto.setPriceUSD(formatPrice(usdRate.multiply(yacht.getPrice())));
        dto.setPriceNOK(formatPrice(nokRate.multiply(yacht.getPrice())));
        dto.setMainImageKey(yacht.getMainImageKey());
        // Yacht Model set
        dto.setMake(yacht.getYachtModel().getMake());
        dto.setModel(yacht.getYachtModel().getModel());
        dto.setYear(yacht.getYachtModel().getYear());
        dto.setLengthOverall(yacht.getYachtModel().getLengthOverall());
        dto.setBeamWidth(yacht.getYachtModel().getBeamWidth());
        dto.setDraftDepth(yacht.getYachtModel().getDraftDepth());
        dto.setKeelType(yacht.getYachtModel().getKeelType().getName());
        dto.setFuelType(yacht.getYachtModel().getFuelType().getName());
        // Yacht Images set
        dto.setYachtImages(yacht.getYachtImages());
        // Country
        dto.setCountry(yacht.getCountry().getName());
        // Town
        dto.setTown(yacht.getTown().getName());
        // Yacht Detail set
        dto.setCabin(yacht.getYachtDetail().getCabin());
        dto.setBerth(yacht.getYachtDetail().getBerth());
        dto.setHeads(yacht.getYachtDetail().getHeads());
        dto.setShower(yacht.getYachtDetail().getShower());
        dto.setDescription(yacht.getYachtDetail().getDescription());
        // Owner info set
        dto.setFirstName(yacht.getOwnerInfo().getFirstName());
        dto.setLastName(yacht.getOwnerInfo().getLastName());
        dto.setTelephone(yacht.getOwnerInfo().getTelephone());
        dto.setEmail(yacht.getOwnerInfo().getEmail());
        // Created at
        dto.setCreatedAt(yacht.getCreatedAt());

        return dto;
    }

    private void updateFields(Yacht yacht, YachtRequestDto dto) {
        yacht.setFeatured(dto.isFeatured());
        yacht.setVatIncluded(dto.isVatIncluded());
        yacht.setPrice(dto.getPrice());
        yacht.setYachtModel(yachtModelService.findId(dto.getYachtModelId()));
        yacht.setCountry(countryService.findId(dto.getCountryId()));
        yacht.setTown(townService.findTownById(dto.getTownId()));
        yacht.setYachtDetail(yachtDetailService.findId(dto.getYachtDetailId()));
        yacht.setOwnerInfo(ownerInfoService.findId(dto.getOwnerInfoId()));
    }

    private String formatPrice(BigDecimal price) {
        BigDecimal rounded = price.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return rounded.toPlainString();
    }
}
