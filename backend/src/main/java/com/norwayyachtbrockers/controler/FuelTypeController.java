package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fuelTypes")
public class FuelTypeController {
    @GetMapping
    public List<String> getAllFuelTypes() {
        return Arrays.stream(FuelType.values())
                .map(FuelType::toString)
                .collect(Collectors.toList());
    }
}
