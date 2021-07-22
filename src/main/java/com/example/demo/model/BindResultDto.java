package com.example.demo.model;

import lombok.Data;

@Data
public class BindResultDto {
    private String field;
    private String rejectedValue;
    private String code;
    private String message;

}
