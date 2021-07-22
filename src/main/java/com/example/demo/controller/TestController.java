package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test() {
        long currentTime = System.currentTimeMillis();
        System.out.println("DemoApplication.index");
        System.out.println("currentTime = " + currentTime);

        return "Hello World!";
    }

}
