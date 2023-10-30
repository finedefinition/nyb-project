package com.norwayyachtbrockers.dto.request;

import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VesselRequestDto {
    private boolean featuredVessel;

    private String vesselMake;

    private String vesselModel;

    private BigDecimal vesselPrice;

    private int vesselYear;

    private String vesselLocationCountry;

    private String vesselLocationState;

    private BigDecimal vesselLengthOverall;

    private BigDecimal vesselBeam;

    private BigDecimal vesselDraft;

    private int vesselCabin;

    private int vesselBerth;

    private FuelType fuelType;

    private KeelType keelType;

    private int engineQuantity;

    private String vesselDescription;

}
