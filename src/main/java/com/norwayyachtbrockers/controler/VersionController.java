package com.norwayyachtbrockers.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {

    @GetMapping
    public ResponseEntity versionCheck() {
        return ResponseEntity.ok("v 3.0.0 Update year");
    }
}
