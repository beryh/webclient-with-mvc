package com.kakaobank.demo.controllers;

import com.kakaobank.demo.common.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreController {
    @GetMapping("/slow")
    private Payload getAllMessages(@RequestParam("key") String key) throws InterruptedException {
        Thread.sleep(10000L); // delay

        return new Payload(key, "value");
    }
}
