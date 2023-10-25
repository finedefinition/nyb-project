package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.enums.FuelType;
import com.norwayyachtbrockers.model.enums.KeelType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/keelTypes")
public class KeelTypeController {

    @GetMapping
    public List<Map<String, String>> getAllKeelTypes() {
        return Arrays.stream(KeelType.values())
                .map(keelType -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", keelType.name());
                    map.put("value", keelType.toString());
                    return map;
                })
                .collect(Collectors.toList());
    }
}

