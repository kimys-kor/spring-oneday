package com.oneday.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping(value = "/test")
    public String Test(
    ) {
        return "hi";
    }
}
