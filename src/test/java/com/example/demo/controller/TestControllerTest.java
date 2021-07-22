package com.example.demo.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class TestControllerTest {
    private MockMvc mockMvc;

    @Autowired
    TestController testController;

    @BeforeEach
    void setUp() {
        System.out.println("TestControllerTest.setUp");
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @AfterEach
    void tearDown() {
        System.out.println("TestControllerTest.tearDown");
    }

    @Test
    void test1() throws Exception {
        System.out.println("TestControllerTest.test1");
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }
}