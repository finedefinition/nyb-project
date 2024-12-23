package com.norwayyachtbrockers.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class HealtCheckController {

    @GetMapping
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("v 2.0.0");
    }
}
