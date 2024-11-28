package com.norwayyachtbrockers.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class YachtUpdateRequestDto {

    private Long id;

    @JsonProperty("yacht_vat")
    private Boolean vatIncluded; // Используем Boolean вместо boolean

    @JsonProperty("yacht_price")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive value.")
    @DecimalMax(value = "5000000", message = "Price must not exceed 5 000 000 euro.")
    private BigDecimal price;

    @JsonProperty("yacht_model_make")
    @Size(min = 3, max = 30, message = "Make must be at least 3 characters long and less than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-]*$", message = "Make must start with a capital letter and can include letters, spaces, and hyphens.")
    private String make;

    @JsonProperty("yacht_model_model")
    @Size(min = 1, max = 30, message = "Model must be at least 1 character long and less than 30 characters.")
    private String model;

    @JsonProperty("yacht_model_year")
    @Min(value = 1930, message = "Year must be no earlier than 1930.")
    @Max(value = 2100, message = "Year must be no later than 2100.")
    private Integer year;

    @JsonProperty("yacht_model_length")
    @DecimalMin(value = "2.5", message = "Length overall must be at least 2.5 meters.")
    @DecimalMax(value = "300", message = "Length overall must not exceed 300 meters.")
    private BigDecimal lengthOverall;

    @JsonProperty("yacht_model_width")
    @DecimalMin(value = "1", message = "Beam width must be at least 1 meter.")
    @DecimalMax(value = "25", message = "Beam width must not exceed 25 meters.")
    private BigDecimal beamWidth;

    @JsonProperty("yacht_model_depth")
    @DecimalMin(value = "0.3", message = "Draft depth must be at least 0.3 meter.")
    @DecimalMax(value = "16", message = "Draft depth must not exceed 16 meters.")
    private BigDecimal draftDepth;

    @JsonProperty("yacht_model_keel_type")
    private String keelType;

    @JsonProperty("yacht_model_fuel_type")
    private String fuelType;

    @JsonProperty("yacht_country")
    private String country;

    @JsonProperty("yacht_town")
    private String town;

    @JsonProperty("yacht_cabin")
    @Min(value = 0, message = "Cabin count must be a non-negative number.")
    @Max(value = 10, message = "Cabin count must not exceed 10.")
    private Integer cabin;

    @JsonProperty("yacht_berth")
    @Min(value = 0, message = "Berth count must be a non-negative number.")
    @Max(value = 20, message = "Berth count must not exceed 20.")
    private Integer berth;

    @JsonProperty("yacht_heads")
    @Min(value = 0, message = "Head count must be a non-negative number.")
    @Max(value = 10, message = "Head count must not exceed 10.")
    private Integer heads;

    @JsonProperty("yacht_shower")
    @Min(value = 0, message = "Shower count must be a non-negative number.")
    @Max(value = 10, message = "Shower count must not exceed 10.")
    private Integer shower;

    @JsonProperty("yacht_description")
    @Size(max = 5000, message = "Description must not exceed 5000 characters.")
    private String description;

    @JsonProperty("first_name")
    @Size(max = 30, message = "First name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-'.,0-9]*$", message = "First name must start with a capital letter.")
    private String firstName;

    @JsonProperty("last_name")
    @Size(max = 30, message = "Last name must be no longer than 30 characters.")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s\\-'.,0-9]*$", message = "Last name must start with a capital letter.")
    private String lastName;

    @JsonProperty("phone_number")
    @Size(min = 7, max = 15, message = "Telephone number must be between 7 and 15 digits long.")
    @Pattern(regexp = "^[0-9]+$", message = "Telephone must consist of only numbers.")
    private String phoneNumber;

    @JsonProperty("e-mail")
    @Email(message = "Please provide a valid email format.")
    private String email;
}
