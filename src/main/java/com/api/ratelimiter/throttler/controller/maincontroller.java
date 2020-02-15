package com.api.ratelimiter.throttler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class maincontroller {

@GetMapping("/")
public String main()
{
    return "welcome";
}
}
