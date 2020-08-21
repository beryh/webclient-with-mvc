package com.kakaobank.demo.controllers;

import com.kakaobank.demo.common.Payload;
import com.kakaobank.demo.services.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/async")
    public List<Payload> async() {
        System.out.println("begin: " + System.currentTimeMillis());
        List<Payload> result = demoService.asyncWithRestTemplate();
        System.out.println("after: " + System.currentTimeMillis());

        return result;
    }

    @GetMapping("/webflux")
    public List<Payload> webFlux() {
        System.out.println("begin: " + System.currentTimeMillis());
        List<Payload> result = demoService.requestUsingRestTemplate();
        System.out.println("after: " + System.currentTimeMillis());

        return result;
    }
}
