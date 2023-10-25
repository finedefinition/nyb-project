package com.norwayyachtbrockers.controler;

import com.norwayyachtbrockers.model.enums.KeelType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/keelTypes")
public class KeelTypeController {

    @GetMapping
    public List<String> getAllKeelTypes() {
        return Arrays.stream(KeelType.values())
                .map(KeelType::toString)
                .collect(Collectors.toList());
    }
}

