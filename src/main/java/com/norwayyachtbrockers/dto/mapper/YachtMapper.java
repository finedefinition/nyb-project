package com.norwayyachtbrockers.dto.mapper;

import com.norwayyachtbrockers.dto.request.FullYachtRequestDto;
import com.norwayyachtbrockers.dto.request.YachtRequestDto;
import com.norwayyachtbrockers.dto.response.YachtCrmFrontendResponseDto;
import com.norwayyachtbrockers.dto.response.YachtCrmResponseDto;
import com.norwayyachtbrockers.dto.response.YachtResponseDto;
import com.norwayyachtbrockers.model.Country;
import com.norwayyachtbrockers.model.Fuel;
import com.norwayyachtbrockers.model.Keel;
import com.norwayyachtbrockers.model.OwnerInfo;
import com.norwayyachtbrockers.model.Town;
import com.norwayyachtbrockers.model.User;
import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.model.YachtDetail;
import com.norwayyachtbrockers.model.YachtModel;
import com.norwayyachtbrockers.service.CountryService;
import com.norwayyachtbrockers.service.FuelService;
import com.norwayyachtbrockers.service.KeelService;
import com.norwayyachtbrockers.service.OwnerInfoService;
import com.norwayyachtbrockers.service.TownService;
import com.norwayyachtbrockers.service.YachtDetailService;
import com.norwayyachtbrockers.service.YachtModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class YachtMapper {
    private final YachtModelService yachtModelService;
    private final TownService townService;
    private final CountryService countryService;
    private final YachtDetailService yachtDetailService;
    private final OwnerInfoService ownerInfoService;
    private final KeelService keelService;
    private final FuelService fuelService;


    public Yacht createYachtFromFullYachtRequestDto(FullYachtRequestDto dto) {
        Yacht yacht = new Yacht();
        yacht.setVatIncluded(dto.isVatIncluded());
        yacht.setPrice(dto.getPrice());

        // YachtModel
        String make = dto.getMake();
        String model = dto.getModel();
        Integer year = dto.getYear();
        Long yachtModelId = yachtModelService.getYachtModelId(make, model, year);

        if (yachtModelId == null) {
            // Create new YachtModel
            YachtModel newYachtModel = new YachtModel();
            newYachtModel.setMake(make);
            newYachtModel.setModel(model);
            newYachtModel.setYear(year);

            // Check and create KeelType
            Keel keelType = dto.getKeelType();
            Long keelTypeId = keelService.getKeelTypeIdByName(keelType.getName());
            if (keelTypeId == null) {
                Keel newKeelType = new Keel();
                newKeelType.setName(keelType.getName());
                Keel savedKeelType = keelService.saveKeel(newKeelType);
                keelTypeId = savedKeelType.getId();
            }
            newYachtModel.setKeelType(keelService.findId(keelTypeId));

            // Check and create FuelType
            Fuel fuelType = dto.getFuelType();
            Long fuelTypeId = fuelService.getFuelTypeIdByName(fuelType.getName());
            if (fuelTypeId == null) {
                Fuel newFuelType = new Fuel();
                newFuelType.setName(fuelType.getName());
                Fuel savedFuelType = fuelService.saveFuel(newFuelType);
                fuelTypeId = savedFuelType.getId();
            }
            newYachtModel.setFuelType(fuelService.findId(fuelTypeId));

            newYachtModel.setLengthOverall(dto.getLengthOverall());
            newYachtModel.setBeamWidth(dto.getBeamWidth());
            newYachtModel.setDraftDepth(dto.getDraftDepth());

            YachtModel savedYachtModel = yachtModelService.saveYachtModel(newYachtModel);
            yachtModelId = savedYachtModel.getId();
        }

        yacht.setYachtModel(yachtModelService.findId(yachtModelId));


        // Country
        String country = dto.getCountry();
        Long countryId = countryService.getCountryIdByName(country);
        if (countryId == null) {
            Country newCountry = new Country();
            newCountry.setName(dto.getCountry());
            Country savedCountry = countryService.saveCountry(newCountry);
            countryId = savedCountry.getId();
        }
        yacht.setCountry(countryService.findId(countryId));

        // Town
        String town = dto.getTown();
        Long townId = townService.getTownIdByName(town);
        if (townId == null) {
            Town newTown = new Town();
            newTown.setName(dto.getTown());
            newTown.setCountry(countryService.findId(countryId));
            Town savedTown = townService.saveTown(newTown);
            townId = savedTown.getId();
        }
        yacht.setTown(townService.findTownById(townId));

        // YachtDetail
        YachtDetail yachtDetail = new YachtDetail();
        yachtDetail.setCabin(dto.getCabin());
        yachtDetail.setBerth(dto.getBerth());
        yachtDetail.setShower(dto.getShower());
        yachtDetail.setHeads(dto.getHeads());
        yachtDetail.setDescription(dto.getDescription());
        yacht.setYachtDetail(yachtDetail);

        // OwnerInfo
        String email = dto.getEmail();
        String phoneNumber = dto.getPhoneNumber();
        Long ownerInfoId = ownerInfoService.getOwnerInfoIdByEmailAndTelephone(email, phoneNumber);
        if (ownerInfoId == null) {
            OwnerInfo newOwnerInfo = new OwnerInfo();
            newOwnerInfo.setFirstName(dto.getFirstName());
            newOwnerInfo.setLastName(dto.getLastName());
            newOwnerInfo.setEmail(dto.getEmail());
            newOwnerInfo.setTelephone(dto.getPhoneNumber());
            OwnerInfo savedOwnerInfo = ownerInfoService.save(newOwnerInfo);
            ownerInfoId = savedOwnerInfo.getId();
        }
        yacht.setOwnerInfo(ownerInfoService.findId(ownerInfoId));

        return yacht;
    }

    public Yacht createYachtFromDto(YachtRequestDto dto) {
        Yacht yacht = new Yacht();
        return setFields(yacht, dto);
    }

    public Yacht updateYachtFromDto(Yacht yacht, YachtRequestDto dto) {
        return setFields(yacht, dto);
    }

    public YachtResponseDto convertToDto(Yacht yacht) {
        YachtResponseDto dto = new YachtResponseDto();
        dto.setId(yacht.getId());
        dto.setVatIncluded(yacht.isVatIncluded());
        dto.setPrice(formatPrice(yacht.getPrice()));
        dto.setPriceOld(formatPrice(yacht.getPriceOld()));
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

    public YachtCrmResponseDto convertToCrmDto(Yacht yacht) {
        YachtCrmResponseDto dto = new YachtCrmResponseDto();
        dto.setId(yacht.getId());
        dto.setMainImageKey(yacht.getMainImageKey());
        dto.setMake(yacht.getYachtModel().getMake());
        dto.setModel(yacht.getYachtModel().getModel());
        dto.setCountry(yacht.getCountry().getName());
        dto.setTown(yacht.getTown().getName());
        dto.setFirstName(yacht.getOwnerInfo().getFirstName());
        dto.setLastName(yacht.getOwnerInfo().getLastName());
        dto.setTelephone(yacht.getOwnerInfo().getTelephone());
        dto.setEmail(yacht.getOwnerInfo().getEmail());
        dto.setCreatedAt(yacht.getCreatedAt());
        return dto;
    }

    private Yacht setFields(Yacht yacht, YachtRequestDto dto) {
        yacht.setVatIncluded(dto.isVatIncluded());
        yacht.setPrice(dto.getPrice());
        yacht.setYachtModel(yachtModelService.findId(dto.getYachtModelId()));
        yacht.setCountry(countryService.findId(dto.getCountryId()));
        yacht.setTown(townService.findTownById(dto.getTownId()));
        yacht.setYachtDetail(yachtDetailService.findId(dto.getYachtDetailId()));
        yacht.setOwnerInfo(ownerInfoService.findId(dto.getOwnerInfoId()));
        return yacht;
    }

    public YachtCrmFrontendResponseDto combineYachtData(List<Yacht> yachts) {
        YachtCrmFrontendResponseDto combinedDto = new YachtCrmFrontendResponseDto();

        // Group by make and models
        Map<String, List<String>> makeToModel = yachts.stream()
                .collect(Collectors.groupingBy(
                        yacht -> yacht.getYachtModel().getMake(),
                        Collectors.mapping(yacht -> yacht.getYachtModel().getModel(), Collectors.toList())
                ));
        combinedDto.setMakeToModel(makeToModel);

        // Get distinct keel types
        List<String> keelTypes = yachts.stream()
                .map(yacht -> yacht.getYachtModel().getKeelType().getName())
                .distinct()
                .collect(Collectors.toList());
        combinedDto.setKeelType(keelTypes);

        // Get distinct fuel types
        List<String> fuelTypes = yachts.stream()
                .map(yacht -> yacht.getYachtModel().getFuelType().getName())
                .distinct()
                .collect(Collectors.toList());
        combinedDto.setFuelType(fuelTypes);

        // Group by country and towns
        Map<String, List<String>> countryToTown = yachts.stream()
                .collect(Collectors.groupingBy(
                        yacht -> yacht.getCountry().getName(),
                        Collectors.mapping(yacht -> yacht.getTown().getName(), Collectors.toList())
                ));
        combinedDto.setCountryToTown(countryToTown);

        // Get distinct first names
        List<String> firstNames = yachts.stream()
                .map(yacht -> yacht.getOwnerInfo().getFirstName())
                .distinct()
                .collect(Collectors.toList());
        combinedDto.setFirstName(firstNames);

        // Get distinct last names
        List<String> lastNames = yachts.stream()
                .map(yacht -> yacht.getOwnerInfo().getLastName())
                .distinct()
                .collect(Collectors.toList());
        combinedDto.setLastName(lastNames);

        return combinedDto;
    }

    private String formatPrice(BigDecimal price) {
        BigDecimal rounded = price.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        return rounded.toPlainString();
    }
}
