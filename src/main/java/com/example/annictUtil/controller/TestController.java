package com.example.annictUtil.controller;

import com.example.annictUtil.model.BindResultDto;
import com.example.annictUtil.model.ValidTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@Slf4j
public class TestController {
    private static final long LOOP_COUNT = 100000000;

    @GetMapping("/test")
    public String test() {
        long currentTime = System.currentTimeMillis();
        System.out.println("DemoApplication.index");
        System.out.println("currentTime = " + currentTime);

        return "Hello World!";
    }

    @GetMapping("/valid")
    public ValidTest valid(@Validated ValidTest validTest, BindingResult result) throws Exception {
        System.out.println("validTest = " + validTest);

        if (result.hasErrors()) {
            List<BindResultDto> bindResultDtos = new ArrayList<>();
            result.getFieldErrors().forEach(s -> {
                BindResultDto bindResultDto = new BindResultDto();
                bindResultDto.setField(s.getField());
                bindResultDto.setRejectedValue(s.getRejectedValue().toString());
                bindResultDto.setCode(s.getCode());
                bindResultDto.setMessage(s.getDefaultMessage());
                bindResultDtos.add(bindResultDto);
            });
            throw new Exception(bindResultDtos.toString());
        }

        return validTest;
    }

    /**
     * ArrayListの追加時間計測(ms)
     * @return
     */
    @GetMapping("/array")
    public long array() {
        System.out.println("TestController.array");
        List<String> array = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP_COUNT; i++) {
            array.add("a");
        }
        long totalTime = System.currentTimeMillis() - start;
        return totalTime;
    }

    /**
     * LinkedListの追加時間計測(ms)
     * @return
     */
    @GetMapping("/linked")
    public long linked() {
        System.out.println("TestController.linked");
        List<String> array = new LinkedList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOP_COUNT; i++) {
            array.add("a");
        }
        long totalTime = System.currentTimeMillis() - start;
        return totalTime;
    }
}
