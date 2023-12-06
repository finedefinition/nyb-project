package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.TownResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.service.CountryService;
import com.norwayyachtbrockers.service.OwnerInfoService;
import com.norwayyachtbrockers.service.TownService;
import com.norwayyachtbrockers.service.YachtDetailService;
import com.norwayyachtbrockers.service.YachtModelService;
import org.springframework.stereotype.Component;

@Component
public class YachtMapper {
    private final YachtModelService yachtModelService;

    private final CountryService countryService;

    private final TownService townService;

    private final YachtDetailService yachtDetailService;

    private final OwnerInfoService ownerInfoService;

    private final TownMapper townMapper;

    public YachtMapper(YachtModelService yachtModelService, CountryService countryService, TownService townService,
                       YachtDetailService yachtDetailService, OwnerInfoService ownerInfoService, TownMapper townMapper) {
        this.yachtModelService = yachtModelService;
        this.countryService = countryService;
        this.townService = townService;
        this.yachtDetailService = yachtDetailService;
        this.ownerInfoService = ownerInfoService;
        this.townMapper = townMapper;
    }

    public void updateFromDto(Yacht yacht, YachtRequestDto dto) {
        yacht.setFeatured(dto.isFeatured());
        yacht.setPrice(dto.getPrice());
        yacht.setYachtModel(yachtModelService.findId(dto.getYachtModelId()));
        yacht.setCountry(countryService.findId(dto.getCountryId()));
        yacht.setCountry(countryService.findId(dto.getCountryId()));
        TownResponseDto townResponseDto = townService.findId(dto.getTownId());
        Town town = townMapper.convertToTown(townResponseDto);
        yacht.setTown(town);
        yacht.setYachtDetail(yachtDetailService.findId(dto.getYachtDetailId()));
        yacht.setOwnerInfo(ownerInfoService.findId(dto.getOwnerInfoId()));
    }

    public YachtResponseDto convertToDto(Yacht yacht) {
        YachtResponseDto dto = new YachtResponseDto();
        dto.setId(yacht.getId());
        dto.setFeatured(yacht.isFeatured());
        dto.setPrice(yacht.getPrice());
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
}
