package com.example.demo.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ValidTest {
    @Min(0)
    private int min;

    @Max(10)
    private int max;

    @Range(min = 0, max = 10)
    private int range;

    @CreditCardNumber
    private String creditCardNumber;
}
